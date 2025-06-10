package com.example.aletheia.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.foundation.pager.rememberPagerState
import androidx.lifecycle.ViewModelProvider
import com.example.aletheia.RetrofitInstance
import com.example.aletheia.RetrofitInstance.api
import com.example.aletheia.SQL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineExceptionHandler


class GetPages(context: Context, username: String) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("GetPages", "Erreur coroutine attrapée : ${throwable.message}")
    }

    private val username = username

    private val sharedPreferences = context.getSharedPreferences("getPagesPrefs::$username", Context.MODE_PRIVATE)
    val globalpref = context.getSharedPreferences(username, Context.MODE_PRIVATE)

    private val _visitProfile = MutableStateFlow(false)
    val visitProfile: StateFlow<Boolean> = _visitProfile

    fun setVisitedProfile(value: Boolean) {
        _visitProfile.value = value
    }

    private val _usernamevisited = MutableStateFlow("")
    val usernamevisited: StateFlow<String> = _usernamevisited

    fun setUsernamevisited(value: String) {
        _usernamevisited.value = value
    }

    private val _pages = MutableStateFlow<List<List<String>>>(listOf())
    val pages: StateFlow<List<List<String>>> = _pages



    private val _isEndlessScroll = MutableStateFlow(true)
    val isEndlessScroll: StateFlow<Boolean> = _isEndlessScroll

    private val _invisiblePages = MutableStateFlow<List<List<String>>>(getInvisiblePagesFromPrefs())
    val invisiblePages: StateFlow<List<List<String>>> = _invisiblePages


    private val _creationMenu = MutableStateFlow<Boolean>(false)
    val creationMenu: StateFlow<Boolean> = _creationMenu

    fun setCreationMenu(value: Boolean) {
        _creationMenu.value = value
    }




    private val _newPost = MutableStateFlow(false)
    val newPost: StateFlow<Boolean> = _newPost

    fun setNewPost(value: Boolean) {
        _newPost.value = value
    }

    private val _goChat = MutableStateFlow(false)
    val goChat: StateFlow<Boolean> = _goChat

    fun setGoChat(value: Boolean) {
        _goChat.value = value
    }


    val _campaignName = MutableStateFlow<String>("")
    val campaignName: StateFlow<String> = _campaignName


    val _newPostContentType = MutableStateFlow<String>("")
    val newPostContentType: StateFlow<String> = _newPostContentType
    fun setNewPostContentType(value: String) {
        _newPostContentType.value = value
    }

    val _newPostPrompt = MutableStateFlow<String>("")
    val newPostPrompt: StateFlow<String> = _newPostPrompt
    fun setNewPostPrompt(value: String) {
        _newPostPrompt.value = value
    }

    val _newPostContentOrURL = MutableStateFlow<String>("")
    val newPostContentOrURL: StateFlow<String> = _newPostContentOrURL
    fun setNewPostContentOrURL(value: String) {
        _newPostContentOrURL.value = value
    }

    fun clearCampaignID() {
        Log.d("GetPages", "clearCampaignID called")
        _campaignName.value = ""
    }

    fun goCampaign(campaign: String) {
        _campaignName.value = campaign
        Log.d("GetPages", "goCampaign called with campaign: $campaign. Value of _campaignName: ${_campaignName.value}")
    }




    private val _showHomeTips = MutableStateFlow(false)
    val showHomeTips: StateFlow<Boolean> = _showHomeTips
    fun setShowHomeTips(value: Boolean) {
        _showHomeTips.value = value
    }


    private fun getInvisiblePagesFromPrefs(): List<List<String>> {
        val invisiblePagesString = sharedPreferences.getString("invisiblePages", "")
        return if (!invisiblePagesString.isNullOrEmpty()) {
            invisiblePagesString.split("\n").map { it.split(",") }
        } else {
            emptyList()
        }
    }





    private val _isFirstLaunch = MutableStateFlow(true)
    val isFirstLaunch: StateFlow<Boolean> = _isFirstLaunch

    fun setFirstLaunch(value: Boolean) {
        Log.d("GetPages", "isFirstLaunch value: ${_isFirstLaunch.value}")
        _isFirstLaunch.value = value
    }


    private fun saveInvisiblePagesToPrefs() {
        val invisiblePagesString = _invisiblePages.value.joinToString("\n") { it.joinToString(",") }
        sharedPreferences.edit().putString("invisiblePages", invisiblePagesString).apply()
    }

    fun addInvisiblePage(page: List<String>) {
        _invisiblePages.value += listOf(page)
        removePage(page)
        saveInvisiblePagesToPrefs() // Sauvegarde après ajout
    }

    fun getInvisiblePages(): List<List<String>> {
        return _invisiblePages.value
    }

    fun removePage(pageToRemove: List<String>) {
        _pages.value = _pages.value.filter { it != pageToRemove }
        //savePagesToPrefs() // Sauvegarde après suppression
    }


    private val _filter = MutableStateFlow("None")
    val filter: StateFlow<String> = _filter

    fun setFilter(value: String) {
        _filter.value = value
    }

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing
    fun refreshData() {
        viewModelScope.launch(exceptionHandler) {
            _isRefreshing.value = true
            _pages.value = listOf()
            _isEndlessScroll.value = true
            RetrofitInstance.setNetworkAvailable(true)
            loadPages("", filter.value)
            delay(300)
            _isRefreshing.value = false
        }
    }

    private val _isFirstPage = MutableStateFlow(true)
    val isFirstPage: StateFlow<Boolean> = _isFirstPage

    fun setFirstPage(value: Boolean) {
        _isFirstPage.value = value
    }

    private val _isNetworkAvailable = MutableStateFlow(true)
    val isNetworkAvailable: StateFlow<Boolean> = _isNetworkAvailable

    fun setNetworkAvailable(value: Boolean) {
        _isNetworkAvailable.value = value
    }



    private val _showSeePrompt = MutableStateFlow(false)
    val showSeePrompt: StateFlow<Boolean> = _showSeePrompt
    fun setShowSeePrompt(value: Boolean) {
        _showSeePrompt.value = value
    }

    private val _showComments = MutableStateFlow(false)
    val showComments: StateFlow<Boolean> = _showComments
    fun setShowComments(value: Boolean) {
        _showComments.value = value
    }

    private val _threeDotsMenu = MutableStateFlow(false)
    val threeDotsMenu: StateFlow<Boolean> = _threeDotsMenu
    fun setThreeDotsMenu(value: Boolean) {
        _threeDotsMenu.value = value
    }

    private val _sendContent = MutableStateFlow(false)
    val sendContent: StateFlow<Boolean> = _sendContent
    fun setSendContent(value: Boolean) {
        _sendContent.value = value
    }



    fun loadPages(username : String, filter: String = "None") {

        if (username.isNotEmpty()) { //inutilisé



        } else {
            if (!_isEndlessScroll.value) return


            _pages.value = _pages.value.toMutableList()
            _invisiblePages.value = getInvisiblePagesFromPrefs().toMutableList()
            Log.d("Invisibles", "Invisibles : $invisiblePages")

            viewModelScope.launch(exceptionHandler) {
                try {
                    //globalpref.edit().putBoolean("networkAvailable", true).apply()
                    var i = 1
                    var acc = 1
                    var fail = 0
                    while (acc <= 10 && fail <= 10) {
                        if (RetrofitInstance.api.is_there_content(SQL().isThereContent(filter)) == "1") {


                            setNetworkAvailable(true)

                            val newPage = RetrofitInstance.api.get_content(SQL().getContent(filter))



                            if (newPage.size != 9) {
                                continue
                            }

                            i++
                            if (!_pages.value.contains(newPage) && !_invisiblePages.value.contains(
                                    newPage
                                )
                            ) {
                                Log.d("GetPages", "Nouvelle page : $newPage")
                                _pages.value = _pages.value.toMutableList().apply { add(newPage) }
                                //savePagesToPrefs()
                                acc += i
                            } else {
                                fail += 1
                            }

                        } else {
                            _isEndlessScroll.value = false
                            break
                        }
                    }
                } catch (e: Exception) {
                    Log.d("GetPages", "Erreur lors du chargement des pages : $e, network : ${isNetworkAvailable.value}, pages : ${pages.value}")
                    //globalpref.edit().putBoolean("networkAvailable", false).apply()
                    setNetworkAvailable(false)
                    _isEndlessScroll.value = false
                }
            }
        }
    }
}


class GetPagesViewModelFactory(private val context: Context, private val username: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GetPages::class.java)) {
            return GetPages(context, username) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
