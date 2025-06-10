package com.example.aletheia

import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Streaming
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.delay
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response

import java.io.InputStreamReader
import java.net.SocketTimeoutException
import java.net.UnknownHostException


val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()




class FlowResponseConverter : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        if (type.toString().contains("Flow")) {
            return Converter<ResponseBody, Flow<String>> { responseBody ->
                flow {
                    val reader = InputStreamReader(responseBody.byteStream())
                    val buffer = CharArray(1024) // Utiliser un buffer fixe

                    try {
                        while (true) {
                            val read = reader.read(buffer) // Lire dans le buffer
                            if (read <= 0) break // Fin du stream si -1 ou 0

                            // Convertir le buffer en String et émettre
                            val chunk = String(buffer, 0, read)
                            Log.d("chunk", "Received chunk: $chunk")
                            emit(chunk)

                            // Petit délai pour permettre le traitement
                            delay(10)
                        }
                    } catch (e: Exception) {
                        Log.d("Error in stream processing", e.toString())
                        emit("Error: ${e.message}")
                    } finally {
                        reader.close()
                        responseBody.close()
                    }
                }.flowOn(Dispatchers.IO)
            }
        }
        return null
    }
}

data class SendImage(
    val type: String,
    val image: String?, // image en base64
    val name : String
)



fun bitmapToBase64(bitmap: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}




// HTTP
interface NAPyServerAPI {

    @POST("/saveImage")
    suspend fun saveImage(@Body image: SendImage): String
    @POST("/generateImage")
    @Streaming
    suspend fun generateImage(@Body requestBody: Map<String, String>): ResponseBody
    @POST("/assistant_route")
    @Streaming
    suspend fun sendPrompt(@Body prompt: Map<String, String>): Flow<String> //texte (string) ou image base64 (string) en retour
    @POST("/get_user_name")
    suspend fun get_user_name(@Body query: Map<String, String>): String //query, commit
    @POST("/insert_user")
    suspend fun insert_user(@Body query: Map<String, String>): String //query, commit
    @POST("/insert_content")
    suspend fun insert_content(@Body query: Map<String, String>): String //query, commit
    @POST("/creation_page_backup")
    suspend fun send_backup(@Body query: Map<String, String>): String //query, commit
    @POST("/is_new_username")
    suspend fun is_new_username(@Body query: Map<String, String>): String //query avec return 1 ou 0
    @POST("/is_new_user")
    suspend fun is_new_user(@Body query: Map<String, String>): String //query avec return 1 ou 0
    @POST("/login")
    suspend fun login(@Body query: Map<String, String>): String //query avec return 1 ou 0
    @POST("/are_there_models")
    suspend fun are_there_models(@Body query: Map<String, String>): String //query avec return 1 ou 0
    @POST("/is_there_user_content")
    suspend fun is_there_user_content(@Body query: Map<String, String>): String //query avec return 1 ou 0
    @POST("/is_there_content")
    suspend fun is_there_content(@Body query: Map<String, String>): String //query avec return 1 ou 0
    @POST("/get_content")
    suspend fun get_content(@Body query: Map<String, String>): MutableList<String> //query avec return list
    @POST("/get_models")
    suspend fun get_models(@Body query: Map<String, String>): MutableList<String> //query avec return list
    @POST("/get_user_content")
    suspend fun get_user_content(@Body query: Map<String, String>): MutableList<List<String>> //query avec return list
    @POST("/get_profile_pic")
    suspend fun profilePic(@Body query: Map<String, String>): String //query avec return 1 ou 0
    @POST("/get_backup")
    suspend fun get_backup(@Body query: Map<String, String>): String //renvoie le json du backup
    @POST("/get_user_data")
    suspend fun get_user_data(@Body query: Map<String, String>): String
    @POST("/save_user_data")
    suspend fun save_user_data(@Body query: Map<String, String>): String
    @POST("/generateAudio")
    suspend fun generateAudio(@Body requestBody: Map<String, String>): String
}




object RetrofitInstance {
    const val BASE_URL = "http://192.168.10.8:8000/"

    private val _isNetworkAvailable = MutableStateFlow(true)
    val isNetworkAvailable: StateFlow<Boolean> = _isNetworkAvailable

    fun setNetworkAvailable(value: Boolean) {
        _isNetworkAvailable.value = value
    }



    // Création d'un client OkHttp avec des timeouts
    val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS) // Timeout de connexion
        .readTimeout(30, TimeUnit.SECONDS)    // Timeout de lecture
        .writeTimeout(30, TimeUnit.SECONDS)   // Timeout d'écriture
        .addInterceptor { chain ->
            try {
                chain.proceed(chain.request())
            } catch (e: Exception) {
                // Mettre à jour l'état du réseau
                _isNetworkAvailable.value = false

                throw e
            }
        }
        .build()

    // Création de Retrofit avec ce client OkHttp
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client) // Passer le client OkHttp avec les timeouts
        //.addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(FlowResponseConverter()) // Ajouter le convertisseur personnalisé

        .addConverterFactory(MoshiConverterFactory.create(moshi))

        .build()

    // Création de ton API via Retrofit
    val api: NAPyServerAPI = retrofit.create(NAPyServerAPI::class.java)
}


class GraphDB() {

    fun graphInsertUser(Valusername: String, Valpassword: String, Valemail: String, ValfirstName: String, VallastName: String, Valage: String,  perm: Int) : Map<String, String> {

        /*val query =
            "USE usercontentrelations CREATE (u:User {username: '$Valusername', password: '$Valpassword', email: '$Valemail', firstname: '$ValfirstName', lastname: '$VallastName', age: '$Valage', permission: '$perm'})"
            //"INSERT INTO userdata (username, password, email, firstname, lastname, age, permission) VALUES ('$Valusername', '$Valpassword', '$Valemail', '$ValfirstName', '$VallastName', '$Valage', $perm)"
*/

        return mapOf(
            "type" to "graphInsertUser",
            "username" to Valusername,
            "password" to Valpassword,
            "email" to Valemail,
            "firstname" to ValfirstName,
            "lastname" to VallastName,
        )


    }

    fun graphInsertIntoContent(
        UserName: String,
        ContentType: String,
        ContentURL: String,
        AudioURL: String,
        ContentPrompt: String,
        AIContentModel: String,
        AIAudioModel: String,
        Caption: String): Map<String, String> {

/*
        val query =
            "USE usercontentrelations CREATE (c:Content {date: datetime(), username: '$UserName', contenttype: '$ContentType', contenturl: '$ContentURL', audiourl: '$AudioURL', contentprompt: '$ContentPrompt', aicontentmodel: '$AIContentModel', aiaudiomodel: '$AIAudioModel', caption: '$Caption'})"
            //"INSERT INTO Content (Date, UserName, ContentType, ContentURL, AudioURL, ContentPrompt, AIContentModel, AIAudioModel, Caption) VALUES (NOW(), '$UserName', '$ContentType', '$ContentURL', '$AudioURL', '$ContentPrompt', '$AIContentModel', '$AIAudioModel', '$Caption');"
   */
        return mapOf(
            "type" to "graphInsertIntoContent",
            "username" to UserName,
            "contenttype" to ContentType,
            "contenturl" to ContentURL,
            "audiourl" to AudioURL,
            "contentprompt" to ContentPrompt,
            "aicontentmodel" to AIContentModel,
            "aiaudiomodel" to AIAudioModel,
            "caption" to Caption
        )


    }


}



class SQL() {

    /*

        fun insertIntoCampaigns(CampaignName: String,
                                UserName: String,
                                CampaignType: String,
                                GenType: String,
                                CampaignDescription: String,
                                NumberOfDataPts: Int,
                                PerDataPtPrice: Float,
                                AssociatedContent: String): String {


            val query = "INSERT INTO Campaigns (CampaignName, Date, UserName, CampaignType, GenType, CampaignDescription, NumberOfDataPts, PerDataPtPrice, AssociatedContent) VALUES ('$CampaignName', GETDATE(), '$UserName', '$CampaignType', '$GenType', '$CampaignDescription', '$NumberOfDataPts', '$PerDataPtPrice', '$AssociatedContent');"
            return query

        }


        fun isInCampaigns(id : Int) : String {
            val query = "SELECT 1 FROM Campaigns WHERE CampaignID = $id"
            return query

        }

        fun getCampaign(id : Int) : String { //Changer CompanyName en UserName !
            val query = "SELECT CampaignName, Date, UserName, CampaignType, GenType, CampaignDescription, NumberOfDataPts, PerDataPtPrice, AssociatedContent FROM Campaigns WHERE CampaignID = $id;"
            return query
        }

         */


    fun getuserfisrtname(username : String): Map<String, String>{
        /*val query =
            //"USE usercontentrelations MATCH (u:User) WHERE u.username = '$username' RETURN u.firstname"
            "(SELECT firstname FROM userdata WHERE username = '$username')"

         */

        return mapOf(
            "type" to "getuserfirstname",
            "username" to username
        )
    }

    fun getuserlastname(username : String): Map<String, String>{
        /*val query =
            //"USE usercontentrelations MATCH (u:User) WHERE u.username = '$username' RETURN u.lastname"
            "(SELECT lastname FROM userdata WHERE username = '$username')"

         */
        return mapOf(
            "type" to "getuserlastname",
            "username" to username
        )
    }



    fun insertUser(Valusername: String, Valpassword: String, Valemail: String, ValfirstName: String, VallastName: String, Valdateofbirth: String,  perm: Int) : Map<String, String> {

        /*val query =
            //"USE usercontentrelations CREATE (u:User {username: '$Valusername', password: '$Valpassword', email: '$Valemail', firstname: '$ValfirstName', lastname: '$VallastName', age: '$Valage', permission: '$perm'})"
            "INSERT INTO userdata (username, password, email, firstname, lastname, age, permission) VALUES ('$Valusername', '$Valpassword', '$Valemail', '$ValfirstName', '$VallastName', '$Valage', $perm)"
*/

        return mapOf(
            "type" to "insertUser",
            "username" to Valusername,
            "password" to Valpassword,
            "email" to Valemail,
            "firstname" to ValfirstName,
            "lastname" to VallastName,
            "dateofbirth" to Valdateofbirth,
            "permission" to perm.toString()
        )


    }


    fun isNewUserName(Valusername: String): Map<String, String> {

        /*val query =
            //"USE usercontentrelations MATCH (u:User) WHERE u.username = '$Valusername' RETURN u"
            "(SELECT 1 FROM userdata WHERE username = '$Valusername')"

         */

        return mapOf(
            "type" to "isNewUserName",
            "username" to Valusername
        )

    }


    fun isNewUser(Valusername: String, Valpassword: String, Valemail: String, ValfirstName: String, VallastName: String, Valdateofbirth: String,  perm: Int): Map<String, String> {

        /*val query =
            //"USE usercontentrelations MATCH (u:User) WHERE u.username = '$Valusername' AND u.password = '$Valpassword' AND u.email = '$Valemail' AND u.firstname = '$ValfirstName' AND u.lastname = '$VallastName' AND u.age = '$Valage' RETURN u"
            "(SELECT 1 FROM userdata WHERE username = '$Valusername' AND password = '$Valpassword' AND email = '$Valemail' AND firstname = '$ValfirstName' AND lastname = '$VallastName' AND age = '$Valage' AND permission = $perm)"


         */
        return mapOf(
            "type" to "isNewUser",
            "username" to Valusername,
            "password" to Valpassword,
            "email" to Valemail,
            "firstname" to ValfirstName,
            "lastname" to VallastName,
            "dateofbirth" to Valdateofbirth,
            "permission" to perm.toString()
        )

    }


    fun login(Valusername: String, Valpassword: String): Map<String, String> {

        /*val query =
            //"USE usercontentrelations MATCH (u:User) WHERE u.username = '$Valusername' AND u.password = '$Valpassword' RETURN u"
            "(SELECT 1 FROM userdata WHERE username = '$Valusername' AND password = '$Valpassword')"

         */
        return mapOf(
            "type" to "login",
            "username" to Valusername,
            "password" to Valpassword
        )

    }



    fun insertIntoContent(
                          UserName: String,
                          ContentType: String,
                          ContentURL: String,
                          AudioURL: String,
                          ContentPrompt: String,
                          AIContentModel: String,
                          AIAudioModel: String,
                          Caption: String): Map<String, String> {


        /*val query =
            //"USE usercontentrelations CREATE (c:Content {date: datetime(), username: '$UserName', contenttype: '$ContentType', contenturl: '$ContentURL', audiourl: '$AudioURL', contentprompt: '$ContentPrompt', aicontentmodel: '$AIContentModel', aiaudiomodel: '$AIAudioModel', caption: '$Caption'})"
            "INSERT INTO Content (Date, UserName, ContentType, ContentURL, AudioURL, ContentPrompt, AIContentModel, AIAudioModel, Caption) VALUES (NOW(), '$UserName', '$ContentType', '$ContentURL', '$AudioURL', '$ContentPrompt', '$AIContentModel', '$AIAudioModel', '$Caption');"
        */
        return mapOf(
            "type" to "insertIntoContent",
            "username" to UserName,
            "contenttype" to ContentType,
            "contenturl" to ContentURL,
            "audiourl" to AudioURL,
            "contentprompt" to ContentPrompt,
            "aicontentmodel" to AIContentModel,
            "aiaudiomodel" to AIAudioModel,
            "caption" to Caption
        )

    }

    fun isThereContent(filter: String) : Map<String, String> {
        /*val query =
            "SELECT 1 FROM Content"
            //"USE usercontentrelations MATCH (c:Content) RETURN c"

         */

        return mapOf(
            "type" to "isThereContent",
            "filter" to filter
        )
    }

    fun getContent(filter: String) : Map<String, String> {
        /*val query =
            //"USE usercontentrelations MATCH (c:Content) WITH c, RAND() AS randomValue ORDER BY randomValue LIMIT 1 RETURN c"
            "SELECT * FROM Content ORDER BY RANDOM() LIMIT 1;"

         */
        return mapOf(
            "type" to "getContent",
            "filter" to filter
        )
    }

    fun isThereUserContent(username : String) : Map<String, String> {
        /*val query =
            "SELECT 1 FROM Content"
            //"USE usercontentrelations MATCH (c:Content) RETURN c"

         */

        return mapOf(
            "type" to "isThereUserContent",
            "username" to username
        )
    }

    fun getUserContent(username : String) : Map<String, String> {
        /*val query =
            //"USE usercontentrelations MATCH (c:Content) WITH c, RAND() AS randomValue ORDER BY randomValue LIMIT 1 RETURN c"
            "SELECT * FROM Content ORDER BY RANDOM() LIMIT 1;"

         */
        return mapOf(
            "type" to "getUserContent",
            "username" to username
        )
    }



    fun getProfilePic(username: String) : Map<String, String> {
        return mapOf(
            "type" to "getProfilePic",
            "username" to username
        )
    }


    fun areThereModels(gendata: String) : Map<String, String> {

        return mapOf(
            "type" to "areThereModels",
            "gendata" to gendata
        )
    }

    fun getModels(gendata : String, offset: Int) : Map<String, String> {

        return mapOf(
            "type" to "getModels",
            "gendata" to gendata,
            "offset" to offset.toString()
        )
    }

    fun sendCreationBackup(username: String, column: String, json: String) : Map<String, String> {
        return mapOf(
            "type" to "creationPageBackup",
            "column" to column,
            "username" to username,
            "json" to json
        )
    }


    fun getCreationBackup(username: String, column: String) : Map<String, String> {
        return mapOf(
            "type" to "getBackup",
            "column" to column,
            "username" to username
        )
    }


    fun saveUserData(username: String, whichdata: String, userjson: String, savedjson: String, biography: String, customname: String, followings: String, followers: String) : Map<String, String> {
        return mapOf(
            "userjson" to userjson,
            "savedjson" to savedjson,
            "biography" to biography,
            "customname" to customname,
            "whichdata" to whichdata,
            "username" to username,
            "followings" to followings,
            "followers" to followers
        )
    }

    fun getUserData(username: String, whichdata: String) : Map<String, String> {
        return mapOf(
            "whichdata" to whichdata,
            "username" to username
        )
    }


    fun generateAudio(username: String, content: String, audioprompt: String, voice: String): Map<String, String> {
        return mapOf(
            "username" to username,
            "content" to content,
            "audioprompt" to audioprompt,
            "voice" to voice
        )
    }

    fun generateImage(username: String, prompt: String, modelid: String) : Map<String, String> {
        return mapOf(
            "username" to username,
            "prompt" to prompt,
            "modelid" to modelid
        )
    }


}


fun sendAI(prompt: String, model: String) : Map<String, String> {
    return mapOf(
        "type" to "sendAI",
        "prompt" to prompt,
        "model" to model
    )
}
