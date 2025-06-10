package com.example.aletheia.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.aletheia.RetrofitInstance
import com.example.aletheia.SQL
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.runtime.mutableStateOf
import com.example.aletheia.pages.profilepage.resizeBitmap
import com.google.gson.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import java.io.ByteArrayOutputStream
import java.lang.reflect.Type

class MutableStateAdapter : JsonSerializer<MutableState<String>>, JsonDeserializer<MutableState<String>> {
    override fun serialize(
        src: MutableState<String>?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src?.value) // Stocke seulement la String
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): MutableState<String> {
        return mutableStateOf(json?.asString ?: "") // Recrée un MutableState<String>
    }
}



data class AIModel(
    val name: String,
    val id: String,
    val genType: String
)

data class Chat(
    var role: String,
    var content: MutableState<String>,
    val chatId: String = UUID.randomUUID().toString()
)

data class ConversationHistory(
    var history: List<Chat>,
    val index: String = UUID.randomUUID().toString()
)

data class ModelConversationHistory(
    var allhistory: List<ConversationHistory>,
    val index_models: String
)

data class ModelTitles(
    var titles: List<ChatTitle>,
    val index_models: String
)

data class ChatTitle(
    var title: String,
    val index: String
)



class ChatHistoryModel(context: Context, username: String) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("ChatHistoryModel", "Erreur coroutine attrapée : ${throwable.message}")
    }

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(object : TypeToken<MutableState<String>>() {}.type, MutableStateAdapter())
        .create()

    private val username = username


    private val _modelTitles = MutableStateFlow<List<ModelTitles>>(listOf())
    val modelTitles: StateFlow<List<ModelTitles>> = _modelTitles

    private val _modelConversations = MutableStateFlow<List<ModelConversationHistory>>(listOf())
    val modelConversations: StateFlow<List<ModelConversationHistory>> = _modelConversations


    private val _networkAvailable = MutableStateFlow<Boolean>(true)
    val networkAvailable: StateFlow<Boolean> = _networkAvailable

    fun setNetwork(value: Boolean) {
        _networkAvailable.value = value
    }


    private val _tmpNetHandler = MutableStateFlow<Boolean>(true)
    val tmpNetHandler: StateFlow<Boolean> = _tmpNetHandler

    fun setTmpNetHandler(value: Boolean) {
        _tmpNetHandler.value = value
    }



    init {
        viewModelScope.launch(exceptionHandler) {
            try {
                _networkAvailable.value = true

                Log.d("ChatHistoryModel", "Loading chat history")

                val chathistoryjson = RetrofitInstance.api.get_backup(
                    SQL().getCreationBackup(
                        username,
                        "chathistory"
                    )
                )
                val titlelistjson = RetrofitInstance.api.get_backup(
                    SQL().getCreationBackup(
                        username,
                        "titlelist"
                    )
                )
                val mymodelsjson = RetrofitInstance.api.get_backup(
                    SQL().getCreationBackup(
                        username,
                        "mymodels"
                    )
                )

                _tmpNetHandler.value = false

                if (mymodelsjson != "NOBACKUP") {
                    _myModels.value =
                        gson.fromJson(mymodelsjson, object : TypeToken<List<AIModel>>() {}.type)

                    sharedPreferences.edit().putString("myModels", mymodelsjson).apply()
                } else {
                    _myModels.value =
                        listOf(AIModel("Meta Llama 3.1", "meta-llama-3.1-8b-instruct", "text"))
                }

                if (chathistoryjson != "NOBACKUP") {
                    _modelConversations.value = gson.fromJson(
                        chathistoryjson,
                        object : TypeToken<List<ModelConversationHistory>>() {}.type
                    )
                    getChatHistory()
                } else {
                    _modelConversations.value = listOf(
                        ModelConversationHistory(listOf(), "meta-llama-3.1-8b-instruct")
                    )
                }
                if (titlelistjson != "NOBACKUP") {
                    _modelTitles.value = gson.fromJson(
                        titlelistjson,
                        object : TypeToken<List<ModelTitles>>() {}.type
                    )
                } else {
                    _modelTitles.value = listOf(
                        ModelTitles(listOf(), "meta-llama-3.1-8b-instruct")
                    )
                }
            } catch (e: Exception) {
                _tmpNetHandler.value = false
                _networkAvailable.value = false
                Log.e("ChatHistoryModel", "Error loading chat history", e)
            }

        }
    }

    private val sharedPreferences = context.getSharedPreferences("chatviewmodelprefs::$username", Context.MODE_PRIVATE)
    val globalpref = context.getSharedPreferences(username, Context.MODE_PRIVATE)

    val chat = MutableStateFlow<ConversationHistory>(ConversationHistory(listOf(), UUID.randomUUID().toString()))

    private val _AIOutput = MutableStateFlow<String>("")
    val AIOutput: StateFlow<String> = _AIOutput
    fun addToOutput(newAIOutput: String) {
        Log.d("ChatHistoryModel", "New AI output: $newAIOutput, AIOutput: ${_AIOutput.value}")
        _AIOutput.value += newAIOutput
        Log.d("ChatHistoryModel", "Updated AI output: ${_AIOutput.value}")
    }
    fun clearOutput() {
        _AIOutput.value = ""
    }

    private val _startOutput = MutableStateFlow<Boolean>(false)
    val startOutput: StateFlow<Boolean> = _startOutput
    fun setStartOutput(value: Boolean) {
        _startOutput.value = value
    }


    private val _chatHistory = MutableStateFlow<List<ConversationHistory>>(listOf(ConversationHistory(listOf(), UUID.randomUUID().toString())))
    val chatHistory: StateFlow<List<ConversationHistory>> = _chatHistory

    private val _isVisible = MutableStateFlow<Boolean>(false)
    val isVisible: StateFlow<Boolean> = _isVisible

    fun setIsVisible(value: Boolean) {
        _isVisible.value = value
    }

    val id_models = sharedPreferences.getString("index_models", "meta-llama-3.1-8b-instruct")
    val index_models = MutableStateFlow<String>(id_models!!)

    val id = sharedPreferences.getString("index_${index_models.value}", chatHistory.value[0].index)
    val index = MutableStateFlow<String>(id!!)

    val ListTitles = MutableStateFlow<List<ChatTitle>>(listOf())
    val listTitles: StateFlow<List<ChatTitle>> = ListTitles

    //val myModelsFromPrefs = sharedPreferences.getString("myModels", Gson().toJson(listOf(AIModel("Meta Llama 3.1", "meta-llama-3.1-8b-instruct", "text"))))

    private fun getMyModelsFromDB(): List<AIModel> {
        val myModelsString = sharedPreferences.getString("myModels", Gson().toJson(listOf(AIModel("Meta Llama 3.1", "meta-llama-3.1-8b-instruct", "text"))))
        var myModels: List<AIModel> = Gson().fromJson(myModelsString, object : TypeToken<List<AIModel>>() {}.type)
        viewModelScope.launch(exceptionHandler) {
            try {
                _networkAvailable.value = true

                val json = RetrofitInstance.api.get_backup(
                    SQL().getCreationBackup(
                        username,
                        "mymodels"
                    )
                )
                _tmpNetHandler.value = false
                if (json != "NOBACKUP") {
                    myModels = Gson().fromJson(json, object : TypeToken<List<AIModel>>() {}.type)
                }
            } catch (e: Exception) {

                _tmpNetHandler.value = false
                _networkAvailable.value = false
                Log.e("ChatHistoryModel", "Error loading chat history", e)
            }
        }
        return myModels
    }

    //private val _myModels = MutableStateFlow<List<AIModel>>(myModelsFromPrefs.let { Gson().fromJson(it, object : TypeToken<List<AIModel>>() {}.type) } ?: listOf())
    private val _myModels = MutableStateFlow<List<AIModel>>(getMyModelsFromDB())
    val myModels: StateFlow<List<AIModel>> = _myModels

    fun setMyModels(newMyModels: List<AIModel>) {
        _myModels.value = newMyModels
        sharedPreferences.edit().putString("myModels", Gson().toJson(newMyModels)).apply()
        viewModelScope.launch(exceptionHandler) {
            try {
                _networkAvailable.value = true

                RetrofitInstance.api.send_backup(
                    SQL().sendCreationBackup(
                        username,
                        "mymodels",
                        Gson().toJson(newMyModels)
                    )
                )
                _tmpNetHandler.value = false
            } catch (e: Exception) {
                _tmpNetHandler.value = false
                _networkAvailable.value = false
                Log.e("ChatHistoryModel", "Error loading chat history", e)
            }
        }
        //sharedPreferences.edit().putString("myModels", Gson().toJson(newMyModels)).apply()
    }


    private val _isFocused = MutableStateFlow<Boolean>(false)
    val isFocused: StateFlow<Boolean> = _isFocused

    fun setIsFocused(value: Boolean) {
        _isFocused.value = value
    }

    private val _modelsListHistory = MutableStateFlow<List<Pair<String, List<ConversationHistory>>>>(listOf(Pair("Meta Llama 3.1", listOf())))
    val modelsListHistory: StateFlow<List<Pair<String, List<ConversationHistory>>>> = _modelsListHistory

    private val _modelsFromDB = MutableStateFlow<List<List<String>>>(listOf())
    val modelsFromDB: StateFlow<List<List<String>>> = _modelsFromDB

    fun clearModelsFromDB() {
        _modelsFromDB.value = listOf()
    }

    private val _invisibleModelsFromDB = MutableStateFlow<List<List<String>>>(listOf())
    val invisibleModelsFromDB: StateFlow<List<List<String>>> = _invisibleModelsFromDB

    fun addToInvisibleModels(model: List<String>) {
        _invisibleModelsFromDB.value = _invisibleModelsFromDB.value.toMutableList().apply { add(model) }
    }

    fun loadmodelsFromDB(gendata: String) {


            _modelsFromDB.value = _modelsFromDB.value.toMutableList()

            viewModelScope.launch(exceptionHandler) {
                try {
                    globalpref.edit().putBoolean("networkAvailable", true).apply()

                    var offset = 0;
                    var i = 1
                    var acc = 1
                    var fail = 0
                    while (acc <= 10 && fail <= 10) { //peut-être à modifier
                        if (RetrofitInstance.api.are_there_models(SQL().areThereModels(gendata)) == "1") {
                            val newPage = RetrofitInstance.api.get_models(SQL().getModels(gendata, offset))
                            _tmpNetHandler.value = false
                            offset++
                            i++
                            if (!_modelsFromDB.value.contains(newPage) && !_invisibleModelsFromDB.value.contains(
                                    newPage
                                )
                            ) {
                                _modelsFromDB.value = _modelsFromDB.value.toMutableList().apply { add(newPage) }
                                acc += i
                                Log.d("ChatHistoryModel1234", "New model added: $newPage")
                            } else {
                                fail += 1
                            }

                        } else {
                            break
                        }
                    }
                } catch (e: Exception) {
                    _tmpNetHandler.value = false
                    _networkAvailable.value = false
                    Log.e("ChatHistoryModel", "Error loading models from db for addmodelpage", e)
                    globalpref.edit().putBoolean("networkAvailable", false).apply()
                }
            }

    }


    private val _addModelToList = MutableStateFlow<Boolean>(false)
    val addModelToList: StateFlow<Boolean> = _addModelToList

    fun toggleAddModelToList() {
        _addModelToList.value = !_addModelToList.value
    }





    fun putIndexModels(newIndex: String) {
        index_models.value = newIndex
        sharedPreferences.edit().putString("index_models", newIndex).apply()
    }

    fun putIndex(newIndex: String) {
        index.value = newIndex
        sharedPreferences.edit().putString("index_${index_models.value}", newIndex).apply()
    }


    /*
    fun getModel(history: List<ConversationHistory>): String {
        loadModelsFromPreferences()
        return _modelsListHistory.value
            .filter { it.second == history }
            .map { it.first }[0]
    }

    fun getModels(): List<String> {
        loadModelsFromPreferences()
        return _modelsListHistory.value.map { it.first }
    }

    private val _models = MutableStateFlow<List<String>>(getModels())
    val models: StateFlow<List<String>> = _models

    fun setModels(newModels: List<String>) {
        _models.value = newModels
    }






    fun addModelToList(newModel: String, chatHistory: List<ConversationHistory>) {
        loadModelsFromPreferences()
        val updatedModelsList = _modelsListHistory.value.toMutableList()
        updatedModelsList.add(Pair(newModel, chatHistory))
        _modelsListHistory.value = updatedModelsList
        saveModelsToPreferences(updatedModelsList)
        _models.value = getModels()
        Log.d("ChatHistoryModel", "New model added: $newModel")
        Log.d("ChatHistoryModel", "Updated models list: ${getModels()}")
    }

    private fun saveModelsToPreferences(modelsList: List<Pair<String, List<ConversationHistory>>>) {
        //val editor = sharedPreferences.edit()
        val json = Gson().toJson(modelsList)
        viewModelScope.launch {
            RetrofitInstance.api.creationPageBackup(SQL().sendCreationBackup(username, "modelslist", json))
        }
        //editor.putString("models_list", json)
        //editor.apply()
    }

    private fun loadModelsFromPreferences(){
        var result = listOf<Pair<String, List<ConversationHistory>>>(Pair("Meta Llama 3.1", listOf()))
        viewModelScope.launch {
            val json = RetrofitInstance.api.creationPageBackup(SQL().getCreationBackup(username, "modelslist"))
            if (json != "NOBACKUP") {
                result = Gson().fromJson(
                    json,
                    object : TypeToken<List<Pair<String, List<ConversationHistory>>>>() {}.type
                )
            }
            _modelsListHistory.value = result
        }
        /*
        val json = sharedPreferences.getString("models_list", null)
        return if (json != null) {
            val type = object : TypeToken<List<Pair<String, List<ConversationHistory>>>>() {}.type
            Gson().fromJson(json, type)
        } else {
            listOf(Pair("Meta Llama 3.1", listOf()))
        }

         */
    }

    fun removeModel(modelName: String) {
        // Charger les modèles depuis SharedPreferences
        val modelsList = _modelsListHistory.value.toMutableList()

        // Supprimer l'élément correspondant au modèle
        modelsList.removeAll { it.first == modelName }

        // Sauvegarder la nouvelle liste
        saveModelsToPreferences(modelsList)
    }


     */




    private fun getChatHistoryFromPrefs() {
        Log.d("ChatHistoryModel", "Getting chat history from preferences")
        var result = listOf<ModelConversationHistory>()
        viewModelScope.launch(exceptionHandler) {
            try {
                _networkAvailable.value = true
                val json = RetrofitInstance.api.get_backup(
                    SQL().getCreationBackup(
                        username,
                        "chathistory"
                    )
                )
                _tmpNetHandler.value = false
                if (json != "NOBACKUP") {
                    result = gson.fromJson(
                        json,
                        object : TypeToken<List<ModelConversationHistory>>() {}.type
                    )
                }
                _chatHistory.value = result.filter { it.index_models == index_models.value }
                    .firstOrNull()?.allhistory ?: listOf()
            } catch (e: Exception) {
                _tmpNetHandler.value = false
                _networkAvailable.value = false
                Log.e("ChatHistoryModel", "Error getting chat history from preferences", e)
            }
        }
        /*
        val chatHistoryString = sharedPreferences.getString("chatHistory_${index_models.value}", null)
        Log.d("ChatHistoryModel", "Chat history string: $chatHistoryString, index_models: ${index_models.value}")

        return if (!chatHistoryString.isNullOrEmpty()) {
            val type = object : TypeToken<List<ConversationHistory>>() {}.type
            Log.d("ChatHistoryModel", "chthistorystring json: $chatHistoryString")
            gson.fromJson(chatHistoryString, type) ?: emptyList()
        } else {
            emptyList()
        }

         */
    }

    private val _isRegenerating = MutableStateFlow<Boolean>(false)
    val isRegenerating: StateFlow<Boolean> = _isRegenerating

    private val _regeneratePrompt = MutableStateFlow<String>("")
    val regeneratePrompt: StateFlow<String> = _regeneratePrompt

    fun setRegeneratePrompt(value: String) {
        _regeneratePrompt.value = value
    }

    fun setIsRegenerating(value: Boolean) {
        _isRegenerating.value = value
    }

    fun regenerate(chatId: String) {
        val updatedChatHistory = chatHistory.value.toMutableList()
        val chatEntry = updatedChatHistory.find { it.index == index.value }

        if (chatEntry != null) {
            val mutableHistory = chatEntry.history.toMutableList()

            // Trouver l'index du contenu généré
            val indexOfGeneratedContent = mutableHistory.indexOfFirst { it.chatId == chatId }

            // Vérifier si l'élément précédent existe avant d'y accéder
            if (indexOfGeneratedContent > 0) {
                val prompt = mutableHistory[indexOfGeneratedContent - 1]
                setRegeneratePrompt(prompt.content.value)

                // Supprimer le contenu généré et le prompt associé
                mutableHistory.removeAt(indexOfGeneratedContent) // Supprime le contenu généré
                mutableHistory.removeAt(indexOfGeneratedContent - 1) // Supprime le prompt

                // Mettre à jour l'entrée dans updatedChatHistory
                val updatedEntry = chatEntry.copy(history = mutableHistory)
                updatedChatHistory[updatedChatHistory.indexOf(chatEntry)] = updatedEntry

                _chatHistory.value = updatedChatHistory
                saveChatHistoryToPrefs(_chatHistory.value)

                // Lancer la logique de régénération
                setIsRegenerating(true)
            }
        }
    }


    fun deleteChat(chatId: String) {
        val updatedChatHistory = chatHistory.value
            .toMutableList()

        // Trouver l'entrée correspondant à index.value
        val chatEntry = updatedChatHistory.find { it.index == index.value }

        if (chatEntry != null) {
            val mutableHistory = chatEntry.history.toMutableList()

            // Trouver l’index du chat à supprimer
            val indexToRemove = mutableHistory.indexOfFirst { it.chatId == chatId }

            // Supprimer l’élément suivant si possible
            if (indexToRemove != -1 && indexToRemove + 1 < mutableHistory.size) {
                mutableHistory.removeAt(indexToRemove + 1)
            }

            // Supprimer l’élément actuel
            val newHistory = mutableHistory.filterNot { it.chatId == chatId }

            // Mettre à jour la liste dans updatedChatHistory
            val updatedEntry = chatEntry.copy(history = newHistory)
            updatedChatHistory[updatedChatHistory.indexOf(chatEntry)] = updatedEntry
        }

        _chatHistory.value = updatedChatHistory
        saveChatHistoryToPrefs(_chatHistory.value)
    }


    fun deleteChatHistory(index: String) {
        val updatedChatHistory = chatHistory.value.toMutableList()
        updatedChatHistory.removeIf { it.index == index }
        _chatHistory.value = updatedChatHistory
        saveChatHistoryToPrefs(_chatHistory.value)
    }

    fun deleteAllChatHistory() {
        for (id in chatHistory.value.map { it.index }) {
            val updatedChatHistory = chatHistory.value.toMutableList()
            updatedChatHistory.removeIf { it.index == id }
            _chatHistory.value = updatedChatHistory
            saveChatHistoryToPrefs(_chatHistory.value)
        }
    }

    fun clearChatHistory() {
        _chatHistory.value = listOf(ConversationHistory(listOf(), UUID.randomUUID().toString()))
    }



    private fun saveChatHistoryToPrefs(chatHistory: List<ConversationHistory>) {
        val existingConversation = _modelConversations.value.firstOrNull { it.index_models == index_models.value }

        if (existingConversation != null) {
            existingConversation.allhistory = chatHistory
        } else {
            _modelConversations.value += ModelConversationHistory(
                index_models = index_models.value,
                allhistory = chatHistory
            )
        }

        Log.d("ChatHistoryModel", "modelconversation value : ${_modelConversations.value}")
        Log.d("ChatHistoryModel", "chathistory : ${chatHistory}")

        val modelConversationsString = gson.toJson(_modelConversations.value)

        viewModelScope.launch(exceptionHandler) {
            try {
                _networkAvailable.value = true

                RetrofitInstance.api.send_backup(
                    SQL().sendCreationBackup(
                        username,
                        "chathistory",
                        modelConversationsString
                    )
                )
                _tmpNetHandler.value = false
            } catch (e: Exception) {
                _tmpNetHandler.value = false
                _networkAvailable.value = false
                Log.e("ChatHistoryModel", "Error saving chat history to preferences", e)
            }
        }
        //sharedPreferences.edit().putString("chatHistory_${index_models.value}", chatHistoryString).apply()
    }



    private fun saveTitleListToPrefs(titleList: List<ChatTitle>) {
        val existingModelTitle = _modelTitles.value.firstOrNull { it.index_models == index_models.value } //+= listOf(ModelTitles(titleList, index_models.value))

        if (existingModelTitle != null) {
            existingModelTitle.titles = titleList
        } else {
            _modelTitles.value += listOf(ModelTitles(titleList, index_models.value))
        }

        val modelTitleString = Gson().toJson(_modelTitles.value)

        viewModelScope.launch(exceptionHandler) {
            try {
                _networkAvailable.value = true

                RetrofitInstance.api.send_backup(
                    SQL().sendCreationBackup(
                        username,
                        "titlelist",
                        modelTitleString
                    )
                )
                _tmpNetHandler.value = false
            } catch (e: Exception) {
                _tmpNetHandler.value = false
                _networkAvailable.value = false
                Log.e("ChatHistoryModel", "Error saving title list to preferences", e)
            }
        }

        //sharedPreferences.edit().putString("titleList_${index_models.value}", titleListString).apply()
    }

    private fun getTitleListFromPrefs() {

        var result = listOf<ModelTitles>()
        viewModelScope.launch(exceptionHandler) {
            try {
                _networkAvailable.value = true

                val json = RetrofitInstance.api.get_backup(
                    SQL().getCreationBackup(
                        username,
                        "titlelist"
                    )
                )
                _tmpNetHandler.value = false
                if (json != "NOBACKUP") {
                    result = Gson().fromJson(
                        json,
                        object : TypeToken<List<ModelTitles>>() {}.type
                    )
                }
                ListTitles.value =
                    result.filter { it.index_models == index_models.value }.firstOrNull()?.titles
                        ?: listOf()
            } catch (e: Exception) {
                _tmpNetHandler.value = false
                _networkAvailable.value = false
                Log.e("ChatHistoryModel", "Error getting title list from preferences", e)
            }
        }

        /*
        val titleListString = sharedPreferences.getString("titleList_${index_models.value}", null)

        return if (!titleListString.isNullOrEmpty()) {
            val type = object : TypeToken<List<ChatTitle>>() {}.type
            Gson().fromJson(titleListString, type) ?: emptyList()
        } else {
            emptyList()
        }

         */
    }

    fun getListTitles() : List<ChatTitle> {
        getTitleListFromPrefs()
        return ListTitles.value
    }

    fun addTitle(newTitle: String, index: String) {
        // Create a new list with the existing elements plus the new one
        val updatedList = ListTitles.value.toMutableList()
        updatedList.add(ChatTitle(newTitle, index))

        Log.d("ChatHistoryModel", "New title added: $newTitle")
        Log.d("ChatHistoryModel", "Updated title list: $updatedList")

        // Update the state flow with the new list
        ListTitles.value = updatedList

        // Save to preferences
        saveTitleListToPrefs(updatedList)
    }

    fun removeTitle(index: String) {
        val updatedList = ListTitles.value.toMutableList()
        updatedList.removeIf { it.index == index }
        if (updatedList.isEmpty()) {
            ListTitles.value = updatedList + listOf()
        } else {
            ListTitles.value = updatedList
        }
        saveTitleListToPrefs(updatedList)
    }

    fun removeAllTitles() {
        ListTitles.value = listOf()
        saveTitleListToPrefs(ListTitles.value)
    }

    fun updateTitle(index: String, newTitle: String) {
        val updatedList = ListTitles.value.toMutableList()
        val titleToUpdate = updatedList.find { it.index == index }

        if (titleToUpdate != null) {
            titleToUpdate.title = newTitle
            ListTitles.value = updatedList
            saveTitleListToPrefs(updatedList)
        } else {
            Log.e("ChatHistoryModel", "Failed to update title: no matching index found: $index")
        }
    }



    fun newChat() {
        _chatHistory.value += listOf(ConversationHistory(listOf(), UUID.randomUUID().toString()))
        saveChatHistoryToPrefs(_chatHistory.value)
    }

    fun addChat() {
        val newMessages = getChat().history
        val chatHistoryList = _chatHistory.value.toMutableList()
        Log.d("ADDCHAT", "newMessages: $newMessages")
        Log.d("ADDCHAT", "chatHistoryList: $chatHistoryList")
        Log.d("ADDCHAT", "index: ${index.value}")


        if (chatHistoryList.isNotEmpty()) {
            if (chatHistoryList.filter { it.index == index.value }[0].history != newMessages) {
                chatHistoryList.filter { it.index == index.value }[0].history += newMessages
            }
        } else {
            chatHistoryList.add(ConversationHistory(newMessages, index.value))
        }

        Log.d("ADDCHAT", "chatHistoryList: $chatHistoryList")
        _chatHistory.value = chatHistoryList
        saveChatHistoryToPrefs(_chatHistory.value)
    }


    fun getChatHistory(): List<ConversationHistory> {
        getChatHistoryFromPrefs()
        if (_chatHistory.value.isEmpty()) {
            _chatHistory.value = listOf(ConversationHistory(listOf(), index.value))
        }
        return _chatHistory.value
    }

    private val _noChatsYet = MutableStateFlow<Boolean>(true)
    val noChatsYet: StateFlow<Boolean> = _noChatsYet
    fun setNoChatsYet(value: Boolean) {
        if (getChatHistory().isEmpty()) {
            _noChatsYet.value = value
        } else {
            _noChatsYet.value = false
        }
    }
    fun getChat(): ConversationHistory {
        return chat.value

    }

    private val _editTitle = MutableStateFlow<Boolean>(false)
    val editTitle: StateFlow<Boolean> = _editTitle

    fun setEditTitle(value : Boolean) {
        _editTitle.value = value
    }

    fun saveLazyListState(lazyListState : LazyListState) {
        Log.d("ChatHistoryModel", "Saving lazy list state for index ${index.value}")
        Log.d("ChatHistoryModel", "First visible item index: ${lazyListState.firstVisibleItemIndex}, first visible item scroll offset: ${lazyListState.firstVisibleItemScrollOffset}")
        sharedPreferences.edit().putInt("firstVisibleItemIndex_${index.value}", lazyListState.firstVisibleItemIndex).apply()
        sharedPreferences.edit().putInt("firstVisibleItemScrollOffset_${index.value}", lazyListState.firstVisibleItemScrollOffset).apply()
    }

    fun restoreLazyListState(): LazyListState {
        val firstVisibleItemIndex = sharedPreferences.getInt("firstVisibleItemIndex_${index.value}", 0)
        val firstVisibleItemScrollOffset = sharedPreferences.getInt("firstVisibleItemScrollOffset_${index.value}", 0)

        return LazyListState(firstVisibleItemIndex, firstVisibleItemScrollOffset)
    }







    /*
    fun creationBackup(username: String) {
        viewModelScope.launch {
            try {

                val indexModelsFromPref = sharedPreferences.getString("index_models", "meta-llama-3.1-8b-instruct")
                val myModelsFromPrefs = sharedPreferences.getString("myModels", Gson().toJson(listOf(AIModel("Meta Llama 3.1", "meta-llama-3.1-8b-instruct", "text"))))
                val defaultModelList : List<Pair<String, List<ConversationHistory>>> = listOf(Pair("Meta Llama 3.1", listOf()))
                val modelsListFromPrefs = sharedPreferences.getString("models_list", gson.toJson(defaultModelList))
                /*
                val titleListFromPrefs = sharedPreferences.getString("titleList_${index_models.value}", null)
                val chatHistoryFromPrefs = sharedPreferences.getString("chatHistory_${index_models.value}", null)
                 */


                val allChatHistories = mutableMapOf<String, String>()
                val allTitleLists = mutableMapOf<String, String>()

                for (model in myModels.value) {
                    val modelId = model.id
                    val chatHistory = sharedPreferences.getString("chatHistory_$modelId", null)
                    val titleList = sharedPreferences.getString("titleList_$modelId", null)

                    if (chatHistory != null) {
                        allChatHistories[modelId] = chatHistory
                    }

                    if (titleList != null) {
                        allTitleLists[modelId] = titleList
                    }
                }

                val backupJson = gson.toJson(
                    Backup(
                        username = username,
                        index_models = indexModelsFromPref!!,
                        myModels = myModelsFromPrefs!!,
                        modelsList = modelsListFromPrefs!!,
                        chatHistory = allChatHistories,
                        titleList = allTitleLists
                    )
                )
                Log.d("ChatHistoryModel", "Backup JSON -> DB: $backupJson")

                RetrofitInstance.api.creationPageBackup(SQL().creationPageBackup(username, backupJson))
            } catch (e: Exception) {
                    Log.e("ChatHistoryModel", "Error creating backup", e)
            }
        }
    }

    fun creationRestoreBackup(username: String) {
        viewModelScope.launch {
            try {

                val backupJson = RetrofitInstance.api.creationPageBackup(SQL().getBackup(username))
                Log.d("ChatHistoryModel", "Backup JSON <- DB: $backupJson")

                if (backupJson == "NOBACKUP") {
                    return@launch
                } else {
                    // Solution avec Gson
                    //val jsonContent = backupJson.trim('"').replace("\\\"", "\"")

                    val backup = gson.fromJson(backupJson, Backup::class.java)

                    sharedPreferences.edit().apply {
                        putString("index_models", backup.index_models)
                        putString("myModels", backup.myModels)
                        putString("models_list", backup.modelsList)
                        apply()
                    }

                    val allChatHistories = backup.chatHistory.toMutableMap()
                    val allTitleLists = backup.titleList.toMutableMap()

                    val myModelsType = object : TypeToken<List<AIModel>>() {}.type
                    val myModelsList: List<AIModel> = gson.fromJson(backup.myModels, myModelsType)

                    for (model in myModelsList) {
                        val modelId = model.id
                        val chatHistory = allChatHistories[modelId]
                        val titleList = allTitleLists[modelId]


                        if (chatHistory != null) {
                            sharedPreferences.edit().putString("chatHistory_$modelId", chatHistory)
                                .apply()

                        }

                        if (titleList != null) {
                            sharedPreferences.edit().putString("titleList_$modelId", titleList)
                                .apply()
                        }
                    }
                }


            } catch (e: Exception) {
                Log.e("ChatHistoryModel", "Error restoring backup", e)
            }
        }
    }

     */

    /*
    private val _imageBitmap = MutableStateFlow<Bitmap?>(null)
    val imageBitmap: StateFlow<Bitmap?> = _imageBitmap

    private val _responseBitmap = MutableStateFlow<String>("")
    val responseBitmap: StateFlow<String> = _responseBitmap

    fun fetchImage(username: String, prompt: String, modelid: String) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                val response = RetrofitInstance.api.generateImage(SQL().generateImage(username, prompt, modelid))
                val bitmap = BitmapFactory.decodeStream(response.byteStream()) // Convertir le flux binaire en Bitmap
                _imageBitmap.value = bitmap

                val stream = ByteArrayOutputStream()
                val resizedBitmap = resizeBitmap(bitmap, 1024, 1024) // Ajuste selon ton besoin
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
                val byteArray = stream.toByteArray()
                val encoded = Base64.encodeToString(byteArray, Base64.DEFAULT)
                _responseBitmap.value = encoded

                Log.d("ImageViewModel", "Image récupérée avec succès")
            } catch (e: Exception) {
                Log.e("ImageViewModel", "Erreur lors de la récupération de l'image", e)
            }
        }
    }

     */






}

class ChatHistoryModelFactory(private val context: Context, private val username: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatHistoryModel::class.java)) {
            return ChatHistoryModel(context, username) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}