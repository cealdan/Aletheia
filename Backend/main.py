from neo4j import GraphDatabase
from fastapi import FastAPI, HTTPException, Body
from pydantic import BaseModel
from fastapi.responses import JSONResponse, FileResponse, StreamingResponse
from typing import Dict, Any
import serial
#import data_processing
import pyodbc
import matplotlib.pyplot as plt
import numpy as np
from flask_cors import CORS
import os
import io
import bcrypt
import psycopg2
from psycopg2 import sql
import requests
import base64
from dotenv import load_dotenv
load_dotenv(dotenv_path="variables.env")

from huggingface_hub import InferenceClient


from threading import Lock
lock = Lock()

from openai import OpenAI

#Lm Studio
client = OpenAI(base_url="http://127.0.0.1:1234/v1", api_key="lm-studio")

# Connexion à Neo4j
URI = os.getenv("neo4j_uri") 
USERNAME = os.getenv("neo4j_username")
PASSWORD = os.getenv("neo4j_password")

# En-têtes de la requête HTTP pour se connecter à Neo4j
headers = {
    "Content-Type": "application/json",
    "Authorization": f"Basic {base64.b64encode(f'{USERNAME}:{PASSWORD}'.encode()).decode()}"
}

driver = GraphDatabase.driver(URI, auth=(USERNAME, PASSWORD))

#HF
HF_API_KEY=os.getenv("HF_API_KEY")


#OpenAI
OPENAI_API_KEY=os.getenv("OPENAI_API_KEY")

# Connexion à PostgreSQL
PG_URI = os.getenv("postgresql_uri")
pg_connection = psycopg2.connect(PG_URI)
pg_cursor = pg_connection.cursor()


PREVIEW_DIR= "audios/tts_preview"
VIDEO_DIR = "videos"  
AUDIO_DIR = "audios"  
IMAGE_DIR = "images"


app = FastAPI()

class ImageRequest(BaseModel):
    image: str
    name: str
    type: str

import uuid



@app.post('/saveImage')
async def save_image(requestBody: Dict[Any, Any]):
    try:
        image = requestBody.get('image')
        name = requestBody.get('name').strip()
        type = requestBody.get('type')

        random_id = str(uuid.uuid4())

        url = f"{name}_profilePic"

        if (type == "profile"):

            query = "INSERT INTO userpagedata (username, profile_pic_url) VALUES (%s, %s) ON CONFLICT (username) DO UPDATE SET profile_pic_url = EXCLUDED.profile_pic_url;"

            pg_cursor.execute(query, (name, url))
            pg_connection.commit()

                

        # Vérification si les données sont présentes
        if not image or not name:
            raise HTTPException(status_code=400, detail="Missing image or name")

        # Décoder l'image en base64
        image_data = base64.b64decode(image)

        # Vérification du dossier images
        if not os.path.exists('images'):
            os.makedirs('images')

        # Sauvegarder l'image dans un fichier

        if (type == "profile"):
            with open(f"images/{url}.png", "wb") as f:
                f.write(image_data)
        else:
            with open(f"images/{name}.png", "wb") as f:
                f.write(image_data)

        return JSONResponse("Image generated successfully")

    except Exception as e:
        print(f"error save image : {e}")
        raise HTTPException(status_code=500, detail=f"Error: {str(e)}")


class GenImageRequest(BaseModel):
    prompt:str

@app.post('/generateImage') #à modifier pour gérer différents modèles de différentes API évenutellement (autre que openai), voire des modèles en local
async def gen_image(requestBody : dict):
    myPrompt = requestBody.get('prompt')
    modelid = requestBody.get('modelid')
    api_key = HF_API_KEY

    #TrueClient = OpenAI(api_key=api_key)

    try:

        """
        response = TrueClient.images.generate(
            model=modelid, #ex: "dall-e-3"
            prompt=myPrompt,
            size="1024x1024",
            quality="standard",
            n=1,
        )

        image_url = response.data[0].url
        print(f"✅ Image générée : {image_url}")

        # Télécharger l'image
        img_response = requests.get(image_url)
        if img_response.status_code != 200:
            raise HTTPException(status_code=500, detail=f"Error: {str(e)}")

        """
        client = InferenceClient(
            provider="fal-ai",
            api_key=api_key,
        )

        # output is a PIL.Image object
        image = client.text_to_image(
            myPrompt,
            model=modelid,
        )

        """
        # Charger l'image en mémoire pour la renvoyer
        image_io = io.BytesIO(image.content)
        image_io.seek(0)
        """

        # Convertir l'image en binaire dans un buffer mémoire
        image_io = io.BytesIO()
        image.save(image_io, format='PNG')
        image_io.seek(0)  # Revenir au début pour lecture

        # Retourner l'image en réponse HTTP
        return StreamingResponse(image_io, media_type='image/png')
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Error: {str(e)}")
    
from pathlib import Path

@app.post('/generateAudio')
async def gen_audio(requestBody: dict):
    username = requestBody.get('username')
    Input = requestBody.get('content')
    audioPrompt = requestBody.get('audioprompt')
    Voice = requestBody.get('voice')

    api_key = OPENAI_API_KEY
    AudioClient = OpenAI(api_key=api_key)

    random_id = str(uuid.uuid4())



    try:

        speech_file_path = AUDIO_DIR + f"/{Voice}_{username}_{random_id}.mp3"
        with AudioClient.audio.speech.with_streaming_response.create(
            model="gpt-4o-mini-tts",
            voice=Voice,
            input=Input,
            instructions=audioPrompt,
        ) as response:
            response.stream_to_file(speech_file_path)

        #audio_io = io.BytesIO(speech_file_path.read_bytes())
        return f"{Voice}_{username}_{random_id}" #StreamingResponse(audio_io, media_type="audio/wav")
    except Exception as e:
        print(f"Erreur dans la génération de l'audio : {str(e)}") 
        raise HTTPException(status_code=500, detail=f"Error: {str(e)}")



@app.get('/video/{video}')
async def serve_video(video):
    try:
        videopath = os.path.join(VIDEO_DIR, f"{video}.mp4")
        
        
        if not os.path.exists(videopath):  # Vérifie si le fichier existe
            raise HTTPException(status_code=404, detail=f"Error: media non trouvée")

        return FileResponse(videopath, media_type="video/mp4")
    except Exception as e:
        print(f"erreur video : {e}")

@app.get('/image/{image}')
async def serve_image(image):
    imagepath = os.path.join(IMAGE_DIR, f"{image}.png")
    
    if not os.path.exists(imagepath):  # Vérifie si le fichier existe
            raise HTTPException(status_code=404, detail=f"Error: media non trouvée")

    return FileResponse(imagepath, media_type="image/png")

@app.get('/audio/{audio:path}')
async def serve_audio(audio):
    try:
        if audio.startswith("tts_preview/"):
            audiopath = os.path.join(AUDIO_DIR, f"{audio}.wav")
        else:
            audiopath = os.path.join(AUDIO_DIR, f"{audio}.mp3")
        
        if not os.path.exists(audiopath):
            raise HTTPException(status_code=404, detail=f"Error: media not found")
        
        if audiopath.endswith(".wav"):
            return FileResponse(audiopath, media_type="audio/wav")
        else:
            return FileResponse(audiopath, media_type="audio/mp3")
    except Exception as e:
        print(f"erreur audio : {e}, audiopath : {audiopath}")
        raise HTTPException(500, str(e))


class AssistantRequest(BaseModel):
    prompt:str

'''
@app.post('/assistant_route')
def getAI(requestBody: Dict[Any, Any]): #requestBody: str = Body()
    with lock:
        try:
            prompt = requestBody.get('prompt')
            modelName = requestBody.get('model')

            querymodel = "SELECT ModelID FROM Models WHERE ModelName = %s"

            pg_cursor.execute(querymodel, (modelName,))  # Exécuter la requête SQL dans PostgreSQL

            results = pg_cursor.fetchall() 
            print(f"prompt : {prompt}, modelname = {modelName}, results = {results}")
            modelID = results[0][0]

            print(f"prompt : {prompt}, modelname = {modelName}, modelID = {modelID}")

            if not prompt or not modelName:
                return "Missing prompt or model", 400
            
            completion = client.chat.completions.create(
                #stream = True,
                model= modelID, #"meta-llama-3.1-8b-instruct", #à modifier avec une variable qui dépend du choix de l'utilisateur sur l'app
                messages=[
                    {"role": "system", "content": ''''''
                    You are an artificial intelligence model, a personal creation assistant on Aletheia.
                    Aletheia is an application where its users create AI generated content and share it with the world.
                    You, as a creation assistant, you help them respectfully and do as you're told as long as it doesn't break any moral rule.
                    Your responses are short.
                    IMPORTANT : Always respond in the language spoken in the user's last input.
                    IMPORTANT : Do not exceed 50 words.
                    IMPORTANT : Do not start your responses with " :", start directly.
                    ''''''},
                    {"role": "user", "content": prompt}
                ],
                temperature=0.7,
            )

            return JSONResponse(completion.choices[0].message.content)
        
        except Exception as e:
            print( f"Unexpected error: {str(e)}", 500)
'''

from fastapi import APIRouter, HTTPException, Request
from fastapi.responses import StreamingResponse
import json

# Créer une réponse en streaming
def generate(prompt, modelID):
    # Appeler l'API avec streaming activé
    import sys
    sys.stdout.flush()

    try:
        stream = client.chat.completions.create(
            stream=True,  # Activer le streaming
            model=modelID,
            messages=[
                {"role": "system", "content": '''
                You are an artificial intelligence model, a personal creation assistant on Aletheia.
                Aletheia is an application where its users create AI generated content and share it with the world.
                You, as a creation assistant, you help them respectfully and do as you're told as long as it doesn't break any moral rule.
                IMPORTANT : Always respond in the language spoken in the user's last input.
                IMPORTANT : Do not exceed 300 words.
                IMPORTANT : Do not start your responses with " :", start directly.
                '''},
                {"role": "user", "content": prompt}
            ],
            temperature=0.7,
        )

        # Parcourir les réponses partielles
        for chunk in stream:
            if chunk.choices[0].delta.content:
                yield chunk.choices[0].delta.content
                print(chunk.choices[0].delta.content)
    except Exception as e:
        yield "Error:"
        print(f"Erreur: API OpenAI: {e}")

@app.post('/assistant_route')
async def getAI(requestBody: dict):
    with lock:
        try:
            prompt = requestBody.get('prompt')
            modelName = requestBody.get('model')

            if not prompt or not modelName:
                raise HTTPException(status_code=400, detail="Missing prompt or model")

            # Récupérer l'ID du modèle depuis la base de données
            querymodel = "SELECT ModelID FROM Models WHERE ModelName = %s"
            pg_cursor.execute(querymodel, (modelName,))
            results = pg_cursor.fetchall()
            modelID = results[0][0]


            

            return StreamingResponse(generate(prompt, modelID), media_type="text/event-stream", headers={"Cache-Control": "no-cache"})

        except Exception as e:
            print(f"Unexpected error: {str(e)}")
            raise HTTPException(status_code=500, detail="Internal server error")



# User Routes
@app.post('/get_user_name')
async def get_user_name(requestBody: Dict[Any, Any]):
    try:
        data = requestBody
        if not data:
            return JSONResponse({"error": "Missing data"}), 400
        
        try:
            with pg_connection.cursor() as pg_cursor_user:
                username = data.get('username')
                
                if (data.get('type') == "getuserfirstname"):
                    query = "(SELECT firstname FROM userdata WHERE username = %s)"
                    pg_cursor_user.execute(query, (username,))
                    results = pg_cursor_user.fetchall() 
                    firstname = results[0][0]
                    return JSONResponse(firstname)
                else:  # getuserlastname
                    query = "(SELECT lastname FROM userdata WHERE username = %s)"
                    pg_cursor_user.execute(query, (username,))
                    results = pg_cursor_user.fetchall() 
                    lastname = results[0][0]
                    return JSONResponse(lastname)
        except Exception as e:
            raise HTTPException(status_code=500, detail=f"Error: {str(e)}")
    except Exception as e:
        pg_connection.rollback()
        raise HTTPException(status_code=500, detail=f"Error: {str(e)}")

@app.post('/insert_user')
async def insert_user(requestBody: Dict[Any, Any]):
    try:
        data = requestBody
        if not data:
            return JSONResponse({"error": "Missing data"}), 400
        
        try:
            with pg_connection.cursor() as pg_cursor_insert:
                username = data.get('username')
                password = data.get('password')
                hashed_password = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt()).decode('utf-8')
                email = data.get('email')
                firstname = data.get('firstname')
                lastname = data.get('lastname')
                dateofbirth = data.get('dateofbirth')
                permission = int(data.get('permission'))

                query = "INSERT INTO userdata (username, password, email, firstname, lastname, dateofbirth, permission) VALUES (%s, %s, %s, %s, %s, %s, %s)"

                pg_cursor_insert.execute(query, (username, hashed_password, email, firstname, lastname, dateofbirth, permission))
                pg_connection.commit()

                return JSONResponse("User inserted")
        except Exception as e:
            pg_connection.rollback()
            raise HTTPException(status_code=500, detail=f"Error: {str(e)}")
    except Exception as e:
        pg_connection.rollback()
        raise HTTPException(status_code=500, detail=f"Error: {str(e)}")

@app.post('/is_new_username')
async def is_new_username(requestBody: Dict[Any, Any]):
    try:
        data = requestBody
        if not data:
            return JSONResponse({"error": "Missing data"}), 400
        
        try:
            with pg_connection.cursor() as pg_cursor_username:
                username = data.get('username')

                query = "(SELECT 1 FROM userdata WHERE username = %s)"
                pg_cursor_username.execute(query, (username,))
                
                result = pg_cursor_username.fetchone()
            
                if result is not None:
                    return "1"
                else:
                    return "0"
        except Exception as e:
            print(f"erreur bool route: {e}")
            raise HTTPException(status_code=500, detail=f"Error: {str(e)}")
    except Exception as e:
        print(f"erreur bool route: {e}")
        pg_connection.rollback()
        raise HTTPException(status_code=500, detail=f"Error: {str(e)}")

@app.post('/is_new_user')
async def is_new_user(requestBody: Dict[Any, Any]):
    try:
        data = requestBody
        if not data:
            return JSONResponse({"error": "Missing data"}), 400
        
        try:
            with pg_connection.cursor() as pg_cursor_newuser:
                username = data.get('username')
                password = data.get('password')
                email = data.get('email')
                firstname = data.get('firstname')
                lastname = data.get('lastname')
                dateofbirth = data.get('dateofbirth')
                permission = int(data.get('permission'))

                fetch_password = "SELECT password FROM userdata WHERE username = %s"
                pg_cursor_newuser.execute(fetch_password, (username,))
                fetched_password = pg_cursor_newuser.fetchone()[0]

                query = "(SELECT 1 FROM userdata WHERE username = %s AND password = %s AND email = %s AND firstname = %s AND lastname = %s AND dateofbirth = %s AND permission = %s)"

                if (bcrypt.checkpw(password.encode('utf-8'), fetched_password.encode('utf-8'))):
                    pg_cursor_newuser.execute(query, (username, fetched_password, email, firstname, lastname, dateofbirth, permission))
                    result = pg_cursor_newuser.fetchall()

                    if result is not None:
                        return "1"
                    else:
                        return "0"
        except Exception as e:
            pg_connection.rollback()
            raise HTTPException(status_code=500, detail=f"Error: {str(e)}")
    except Exception as e:
        pg_connection.rollback()
        raise HTTPException(status_code=500, detail=f"Error: {str(e)}")

@app.post('/login')
async def login(requestBody: Dict[Any, Any]):
    try:
        data = requestBody
        if not data:
            return JSONResponse({"error": "Missing data"}), 400
        
        try:
            with pg_connection.cursor() as pg_cursor_login:
                username = data.get('username')
                password = data.get('password')

                fetch_password = "SELECT password FROM userdata WHERE username = %s"
                pg_cursor_login.execute(fetch_password, (username,))
                fetched_password = pg_cursor_login.fetchone()[0]

                if (fetched_password is not None and bcrypt.checkpw(password.encode('utf-8'), fetched_password.encode('utf-8'))):
                    return "1"  
                else:
                    return "0"
        except Exception as e:
            pg_connection.rollback()
            raise HTTPException(status_code=500, detail=f"Error: {str(e)}")
    except Exception as e:
        pg_connection.rollback()
        raise HTTPException(status_code=500, detail=f"Error: {str(e)}")

# Content Routes
@app.post('/insert_content')
async def insert_content(requestBody: Dict[Any, Any]):
    try:
        data = requestBody
        if not data:
            return JSONResponse({"error": "Missing data"}), 400
        
        try:
            with pg_connection.cursor() as pg_cursor_content:
                username = data.get('username')
                contenttype = data.get('contenttype')
                contenturl = data.get('contenturl')
                audiourl = data.get('audiourl')
                contentprompt = data.get('contentprompt')
                aicontentmodel = data.get('aicontentmodel')
                aiaudiomodel = data.get('aiaudiomodel')
                caption = data.get('caption')

                query = "INSERT INTO content (date, username, contenttype, contenturl, audiourl, contentprompt, aicontentmodel, aiaudiomodel, caption) VALUES (NOW(), %s, %s, %s, %s, %s, %s, %s, %s)"

                pg_cursor_content.execute(query, (username, contenttype, contenturl, audiourl, contentprompt, aicontentmodel, aiaudiomodel, caption))
                pg_connection.commit()

                return JSONResponse("Content inserted")
        except Exception as e:
            print(f"erreur insertcontent : {e}")
            pg_connection.rollback()
            raise HTTPException(status_code=500, detail=f"Error: {str(e)}")
    except Exception as e:
        print(f"erreur insertcontent : {e}")
        pg_connection.rollback()
        raise HTTPException(status_code=500, detail=f"Error: {str(e)}")

@app.post('/is_there_content')
async def is_there_content(requestBody: Dict[Any, Any]):
    try:
        data = requestBody
        if not data:
            return JSONResponse({"error": "Missing data"}), 400
        
        try:
            with pg_connection.cursor() as pg_cursor_check:
                filter = data.get('filter')
                if (filter == "None"):
                    query = "SELECT 1 FROM Content"
                    pg_cursor_check.execute(query)
                    result = pg_cursor_check.fetchall()
                else:
                    query = "SELECT 1 FROM Content WHERE ContentType = %s"
                    pg_cursor_check.execute(query, (filter,))
                    result = pg_cursor_check.fetchall()
    
                if result is not None:
                    return "1"
                else:
                    return "0"
        except Exception as e:
            print(f"erreur istherecontent : {e}")
            pg_connection.rollback()
            raise HTTPException(status_code=500, detail=f"Error: {str(e)}")
    except Exception as e:
        print(f"erreur istherecontent : {e}")
        pg_connection.rollback()
        raise HTTPException(status_code=500, detail=f"Error: {str(e)}")

@app.post('/get_content')
async def get_content(requestBody: Dict[Any, Any]):
    try:
        data = requestBody
        if not data:
            return JSONResponse({"error": "Missing data"}), 400
        
        try:
            with pg_connection.cursor() as pg_cursor_get:
                filter = data.get('filter')

                if (filter == "None"):
                    query = "SELECT * FROM Content ORDER BY RANDOM() LIMIT 1;"
                    pg_cursor_get.execute(query)
                    result = pg_cursor_get.fetchall()
                else:
                    query = "SELECT * FROM Content WHERE ContentType = %s ORDER BY RANDOM() LIMIT 1;"
                    pg_cursor_get.execute(query, (filter,))
                    result = pg_cursor_get.fetchall()

                if result:
                    line = []
                    
                    for e0 in result[0]:
                        if type(e0) != str and type(e0) != int:
                            line.append(e0.isoformat())  
                        else:
                            line.append(e0)  
                        
                    return JSONResponse(line)  
                else:
                    return JSONResponse("No results found")
        except Exception as e:
            print(f"erreur getcontent : {e}")
            pg_connection.rollback()
            raise HTTPException(status_code=500, detail=f"Error: {str(e)}")
    except Exception as e:
        print(f"erreur getcontent : {e}")
        pg_connection.rollback()
        raise HTTPException(status_code=500, detail=f"Error: {str(e)}")

@app.post('/is_there_user_content')
async def is_there_user_content(requestBody: Dict[Any, Any]):
    try:
        data = requestBody
        if not data:
            return JSONResponse({"error": "Missing data"}), 400
        
        try:
            with pg_connection.cursor() as pg_cursor_user_check:
                username = data.get('username')
                query = "SELECT 1 FROM Content WHERE UserName = %s LIMIT 1"

                pg_cursor_user_check.execute(query, (username,))
                result = pg_cursor_user_check.fetchall()

                if result is not None:
                    return "1"
                else:
                    return "0"
        except Exception as e:
            print(f"erreur isthereusercontent : {e}")
            pg_connection.rollback()
            raise HTTPException(status_code=500, detail=f"Error: {str(e)}")
    except Exception as e:
        print(f"erreur isthereusercontent : {e}")
        pg_connection.rollback()
        raise HTTPException(status_code=500, detail=f"Error: {str(e)}")

@app.post('/get_user_content') #inutilisé
async def get_user_content(requestBody: Dict[Any, Any]):
    try:
        data = requestBody
        if not data:
            return JSONResponse({"error": "Missing data"}), 400
        
        try:
            with pg_connection.cursor() as pg_cursor_user_content:
                username = data.get('username')
                query = "SELECT * FROM Content WHERE username = %s ORDER BY Date DESC;"
                pg_cursor_user_content.execute(query, (username,))
                result = pg_cursor_user_content.fetchall()

                if result:
                    line = []
                    
                    for e2 in result:
                        tmp = []
                        for elem in e2:
                            if type(elem) != str:
                                tmp.append(elem.isoformat())  
                            else:
                                tmp.append(elem)
                        line.append(tmp)
                        tmp = [] 
                        
                    return JSONResponse(line)  
                else:
                    return JSONResponse("No results found")
        except Exception as e:
            print(f"erreur usergetcontent : {e}")
            pg_connection.rollback()
            raise HTTPException(status_code=500, detail=f"Error: {str(e)}")
    except Exception as e:
        print(f"erreur usergetcontent : {e}")
        pg_connection.rollback()
        raise HTTPException(status_code=500, detail=f"Error: {str(e)}")


@app.post('/get_user_data')
async def send_user_content(requestBody: Dict[Any, Any]):
    try:
        data = requestBody
        if not data:
            return JSONResponse({"error": "Missing data"}), 400
        
        try:
            with pg_connection.cursor() as pg_cursor_getusercontent:
                whichdata = data.get('whichdata')
                username = data.get('username')

                istheredataquery = "SELECT %s FROM userpagedata WHERE username = %s"
                pg_cursor_getusercontent.execute(istheredataquery, (whichdata, username))
                istheredata = pg_cursor_getusercontent.fetchone()

                if istheredata[0] != None:
                

                    if (whichdata == "userposts"):
                        query = "SELECT userposts FROM userpagedata WHERE username = %s;"

                        pg_cursor_getusercontent.execute(query, (username,))

                    elif (whichdata == "savedposts"):
                        query = "SELECT savedposts FROM userpagedata WHERE username = %s;"

                        pg_cursor_getusercontent.execute(query, (username,))

                    elif (whichdata == "biography"):
                        querybio = "SELECT biography FROM userpagedata WHERE username = %s;"

                        pg_cursor_getusercontent.execute(querybio, (username,))
                        

                    elif (whichdata == "customname"):
                        queryname = "SELECT customname FROM userpagedata WHERE username = %s;"

                        pg_cursor_getusercontent.execute(queryname, (username,))

                    elif (whichdata == "followers"):
                        queryer = "SELECT followers FROM userpagedata WHERE username = %s;"

                        pg_cursor_getusercontent.execute(queryer, (username,))

                    elif (whichdata == "followings"):
                        querying = "SELECT followings FROM userpagedata WHERE username = %s;"

                        pg_cursor_getusercontent.execute(querying, (username,))
                        

                    res = pg_cursor_getusercontent.fetchone()
                    if (res[0] != None):
                        return str(res[0])
                    else:
                        return "NULL"
                else:
                    return "NULL"

        except Exception as e:
            print(f"erreur save user content : {e}")
            pg_connection.rollback()
            raise HTTPException(status_code=500, detail=f"Error: {str(e)}")



    except Exception as e:
        print(f"erreur save user content : {e}")
        pg_connection.rollback()
        raise HTTPException(status_code=500, detail=f"Error: {str(e)}")


@app.post('/save_user_data')
async def send_user_content(requestBody: Dict[Any, Any]):
    try:
        data = requestBody
        if not data:
            return JSONResponse({"error": "Missing data"}), 400
        
        try:
            with pg_connection.cursor() as pg_cursor_saveusercontent:
                whichdata = data.get('whichdata')
                username = data.get('username')
                userjson = data.get('userjson')
                savedjson = data.get('savedjson')
                bio = data.get('biography')
                customname = data.get('customname')
                followings = data.get('followings')
                followers = data.get('followers')

                if (whichdata == "userposts"):
                    query = "INSERT INTO userpagedata (username, userposts) VALUES (%s, %s) ON CONFLICT (username) DO UPDATE SET userposts = EXCLUDED.userposts;"

                    pg_cursor_saveusercontent.execute(query, (username, userjson))
                    pg_connection.commit()

                elif (whichdata == "savedposts"):
                    query = "INSERT INTO userpagedata (username, savedposts) VALUES (%s, %s) ON CONFLICT (username) DO UPDATE SET savedposts = EXCLUDED.savedposts;"

                    pg_cursor_saveusercontent.execute(query, (username, savedjson))
                    pg_connection.commit()

                elif (whichdata == "customname"):
                    query = "INSERT INTO userpagedata (username, customname) VALUES (%s, %s) ON CONFLICT (username) DO UPDATE SET customname = EXCLUDED.customname;"

                    pg_cursor_saveusercontent.execute(query, (username, customname))
                    pg_connection.commit()

                elif (whichdata == "biography"):
                    query = "INSERT INTO userpagedata (username, biography) VALUES (%s, %s) ON CONFLICT (username) DO UPDATE SET biography = EXCLUDED.biography;"

                    pg_cursor_saveusercontent.execute(query, (username, bio))
                    pg_connection.commit()

                elif (whichdata == "followings"):
                    query = "INSERT INTO userpagedata (username, followings) VALUES (%s, %s) ON CONFLICT (username) DO UPDATE SET followings = EXCLUDED.followings;"

                    pg_cursor_saveusercontent.execute(query, (username, followings))
                    pg_connection.commit()

                elif (whichdata == "followers"):
                    query = "INSERT INTO userpagedata (username, followers) VALUES (%s, %s) ON CONFLICT (username) DO UPDATE SET followers = EXCLUDED.followers;"

                    pg_cursor_saveusercontent.execute(query, (username, followers))
                    pg_connection.commit()

        except Exception as e:
            print(f"erreur save user content : {e}")
            pg_connection.rollback()
            raise HTTPException(status_code=500, detail=f"Error: {str(e)}")



    except Exception as e:
        print(f"erreur save user content : {e}")
        pg_connection.rollback()
        raise HTTPException(status_code=500, detail=f"Error: {str(e)}")

@app.post('/get_profile_pic')
async def get_profile_pic(requestBody: Dict[Any, Any]):
    try:
        data = requestBody
        if not data:
            return JSONResponse({"error": "Missing data"}), 400
        
        try:
            with pg_connection.cursor() as pg_cursor_pic:
                username = data.get('username')
                query = "SELECT profile_pic_url FROM UserPageData WHERE username = %s"

                try:
                    pg_cursor_pic.execute(query, (username,))
                    result = pg_cursor_pic.fetchone()[0]

                    image_path = f"images/{result}.png"

                    # Ouvre l'image en mode binaire
                    with open(image_path, "rb") as image_file:
                        # Lis l'image et encode en base64
                        encoded_image = base64.b64encode(image_file.read()).decode('utf-8')
                        return JSONResponse(encoded_image)
                
                except Exception as e:
                    print(f"exception getprofilepic")
                    return JSONResponse("null")
        except Exception as e:
            print(f"erreur getprofilepic : {e}")
            pg_connection.rollback()
            raise HTTPException(status_code=500, detail=f"Error: {str(e)}")
    except Exception as e:
        print(f"erreur getprofilepic : {e}")
        pg_connection.rollback()
        raise HTTPException(status_code=500, detail=f"Error: {str(e)}")

# Model Routes
@app.post('/are_there_models')
async def are_there_models(requestBody: Dict[Any, Any]):
    try:
        data = requestBody
        if not data:
            return JSONResponse({"error": "Missing data"}), 400
        
        try:
            with pg_connection.cursor() as pg_cursor_models_check:
                gendata = data.get('gendata')
                query = "SELECT 1 FROM Models WHERE gendata = %s"

                pg_cursor_models_check.execute(query, (gendata,))
                result = pg_cursor_models_check.fetchall()

                if result is not None:
                    return "1"
                else:
                    return "0"
        except Exception as e:
            print(f"erreur aretheremodels : {e}")
            pg_connection.rollback()
            raise HTTPException(status_code=500, detail=f"Error: {str(e)}")
    except Exception as e:
        print(f"erreur aretheremodels : {e}")
        pg_connection.rollback()
        raise HTTPException(status_code=500, detail=f"Error: {str(e)}")

@app.post('/get_models')
async def get_models(requestBody: Dict[Any, Any]):
    try:
        data = requestBody
        if not data:
            return JSONResponse({"error": "Missing data"}), 400
        
        try:
            with pg_connection.cursor() as pg_cursor_models:
                gendata = data.get('gendata')
                offset = int(data.get('offset'))
                query = "SELECT * FROM Models WHERE gendata = %s ORDER BY modelname LIMIT 1 OFFSET %s;" 
                pg_cursor_models.execute(query, (gendata, offset))
                result = pg_cursor_models.fetchall()

                if result:
                    line = []
                    
                    for e1 in result[0]:
                        if type(e1) != str and type(e1) != int:
                            line.append(e1.isoformat())  
                        else:
                            line.append(e1)  
                        
                    return line  
                else:
                    return JSONResponse("No results found")
        except Exception as e:
            print(f"erreur getmodels : {e}")
            pg_connection.rollback()
            raise HTTPException(status_code=500, detail=f"Error: {str(e)}")
    except Exception as e:
        print(f"erreur getmodels : {e}")
        pg_connection.rollback()
        raise HTTPException(status_code=500, detail=f"Error: {str(e)}")

# Backup Routes
@app.post('/creation_page_backup')
async def creation_page_backup(requestBody: Dict[Any, Any]):
    try:
        data = requestBody
        if not data:
            return JSONResponse({"error": "Missing data"}), 400
        try:
            with pg_connection.cursor() as pg_cursor_backup:
                column = data.get('column')
                json_data = data.get('json')
                username = data.get('username')

                print(f"column: {column}, json_data: {json_data}, username: {username}")

                isthereanybackupQuery = "SELECT 1 FROM CreationPageBackup WHERE username = %s"
                pg_cursor_backup.execute(isthereanybackupQuery, (username,))
                isThereAnyBackup = pg_cursor_backup.fetchall()

                if isThereAnyBackup:
                    updateQuery = sql.SQL("""
                        UPDATE CreationPageBackup 
                        SET {} = %s 
                        WHERE username = %s
                    """).format(sql.Identifier(column))
                    pg_cursor_backup.execute(updateQuery, (json_data, username))
                    pg_connection.commit()
                else:
                    insertQuery = """
                    INSERT INTO CreationPageBackup (username, chatHistory, mymodels, titleList) 
                    VALUES (%s, '{}'::jsonb, '{}'::jsonb, '{}'::jsonb)
                    """
                    pg_cursor_backup.execute(insertQuery, (username,))
                    pg_connection.commit()

                    updateQuery2 = sql.SQL("""
                        UPDATE CreationPageBackup 
                        SET {} = %s 
                        WHERE username = %s
                    """).format(sql.Identifier(column))
                    pg_cursor_backup.execute(updateQuery2, (json_data, username))
                    pg_connection.commit()

                return JSONResponse("Backup complete")
        except Exception as e:
            print(f"erreur creationPageBackup : {e}")
            pg_connection.rollback()
            raise HTTPException(status_code=500, detail=f"Error: {str(e)}")
    except Exception as e:
        print(f"erreur creationPageBackup : {e}")
        pg_connection.rollback()
        raise HTTPException(status_code=500, detail=f"Error: {str(e)}")

@app.post('/get_backup')
async def get_backup(requestBody: Dict[Any, Any]):
    try:
        data = requestBody
        if not data:
            return JSONResponse({"error": "Missing data"}), 400
        
        try:
            with pg_connection.cursor() as pg_cursor_getbackup:
                column = data.get('column')
                username = data.get('username')
                
                check_user_query = "SELECT 1 FROM CreationPageBackup WHERE username = %s"
                pg_cursor_getbackup.execute(check_user_query, (username,))
                user_exists = pg_cursor_getbackup.fetchone()
                
                if not user_exists:
                    return "NOBACKUP"
                else:
                    getbackupQuery = sql.SQL("SELECT {} FROM CreationPageBackup WHERE username = %s").format(sql.Identifier(column))
                    pg_cursor_getbackup.execute(getbackupQuery, (username,))
                    getBackupResult = pg_cursor_getbackup.fetchone()
                    
                    if getBackupResult != ({},):
                        return str(getBackupResult[0])
                    else:
                        return "NOBACKUP"
        except Exception as e:
            print(f"Erreur complète : {e}")
            pg_connection.rollback()
            raise HTTPException(status_code=500, detail=f"Error: {str(e)}")
    except Exception as e:
        print(f"Erreur complète : {e}")
        pg_connection.rollback()
        raise HTTPException(status_code=500, detail=f"Error: {str(e)}")
    
    

"""
uvicorn AletheiaServer(HTTP):app --host 0.0.0.0 --reload
"""
