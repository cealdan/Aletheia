package com.example.aletheia.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.aletheia.RetrofitInstance
import com.example.aletheia.SQL
import com.example.aletheia.pages.profilepage.getProfileImage
import com.example.aletheia.showFloatingToast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(context: Context, username: String) : ViewModel() {

    val username = username
    val context = context

    val prefs = context.getSharedPreferences(username, Context.MODE_PRIVATE)

    private val _goToFollowersFollowing = MutableStateFlow<Pair<Boolean, String>>(Pair(false, ""))
    val goToFollowersFollowing: StateFlow<Pair<Boolean, String>> = _goToFollowersFollowing

    fun toggleGoToFollowersFollowing(whichOne : String) {
        _goToFollowersFollowing.value = Pair(!_goToFollowersFollowing.value.first, whichOne)
    }

    private val _followers = MutableStateFlow<List<String>>(emptyList())
    val followers: StateFlow<List<String>> = _followers
    //pour les deux on liste les username et on accède aux infos des comptes grâce à la table userdata
    private val _following = MutableStateFlow<List<String>>(emptyList())
    val following: StateFlow<List<String>> = _following

    private val _userPages = MutableStateFlow<List<List<String>>>(emptyList())
    val userPages: StateFlow<List<List<String>>> = _userPages

    private val _profilePic = MutableStateFlow<Bitmap?>(null)
    val profilePic: StateFlow<Bitmap?> = _profilePic

    private val _savedPages = MutableStateFlow<List<List<String>>>(emptyList())
    val savedPages: StateFlow<List<List<String>>> = _savedPages

    private val _biography = MutableStateFlow<String>("")
    val biography: StateFlow<String> = _biography

    private val _name = MutableStateFlow<String>("")
    val name: StateFlow<String> = _name

    fun setName(value: String) {
        _name.value = value
        saveNameToPrefs()
    }

    fun setBiography(value: String) {
        _biography.value = value
        saveBioToPrefs()
    }

    fun unfollow(usernamevisited: String) {
        _following.value = _following.value.toMutableList().apply { remove(usernamevisited) }
        saveFollowingToPrefs()

        viewModelScope.launch(exceptionHandler) {
            try {
                var thisUserFollowersJson = RetrofitInstance.api.get_user_data(SQL().getUserData(usernamevisited, "followers"))
                if (thisUserFollowersJson != "NULL") {
                    val thisUserFollowers = Gson().fromJson<List<String>>(thisUserFollowersJson, object : TypeToken<List<String>>() {}.type)
                    val updatedFollowers = thisUserFollowers.toMutableList().apply { remove(username) } // Supprime l'élément
                    thisUserFollowersJson = Gson().toJson(updatedFollowers) // Réassigne la nouvelle liste
                } else {
                    thisUserFollowersJson = "NULL"
                }

                RetrofitInstance.api.save_user_data(
                    SQL().saveUserData(
                        usernamevisited,
                        "followers",
                        "",
                        "",
                        "",
                        "",
                        "",
                        thisUserFollowersJson
                    )
                )
            } catch (e: Exception) {
                Log.d("ProfileViewModel", "Erreur lors de : unfollow() : $e")
                RetrofitInstance.setNetworkAvailable(false)
            }
        }
    }

    fun follow(usernamevisited: String) {
        if (usernamevisited == username) return
        _following.value = _following.value.toMutableList().apply { add(usernamevisited) }
        saveFollowingToPrefs()
        viewModelScope.launch(exceptionHandler) {
            try {
                var thisUserFollowersJson = RetrofitInstance.api.get_user_data(SQL().getUserData(usernamevisited, "followers"))
                Log.d("ProfileViewModel", "thisUserFollowersJson : $thisUserFollowersJson")
                Log.d("ProfileViewModel", "usernamevisited : $usernamevisited")
                Log.d("ProfileViewModel", "username : $username")
                if (thisUserFollowersJson != "NULL") {
                    val thisUserFollowers = Gson().fromJson<List<String>>(thisUserFollowersJson, object : TypeToken<List<String>>() {}.type)
                    val updatedFollowers = thisUserFollowers.toMutableList().apply { add(username) } // Met à jour la liste
                    thisUserFollowersJson = Gson().toJson(updatedFollowers) // Réassigne la nouvelle liste
                } else {
                    thisUserFollowersJson = Gson().toJson(listOf(username))
                }

                Log.d("ProfileViewModel", "thisUserFollowersJson2 : $thisUserFollowersJson")

                RetrofitInstance.api.save_user_data(
                    SQL().saveUserData(
                        usernamevisited,
                        "followers",
                        "",
                        "",
                        "",
                        "",
                        "",
                        thisUserFollowersJson
                    )
                )
            } catch (e: Exception) {
                Log.d("ProfileViewModel", "Erreur lors de : follow() : $e")
                RetrofitInstance.setNetworkAvailable(false)
            }
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("ProfileViewModel", "Erreur coroutine attrapée : ${throwable.message}")
    }

    init {
        viewModelScope.launch(exceptionHandler) {
            try {
                getFollowersFromPrefs()
                getFollowingFromPrefs()
                loadProfileImage(prefs, username)
                getNameFromPrefs()
                getBioFromPrefs()
                getUserPagesFromPrefs()
                getSavedPagesFromPrefs()
            } catch (e: Exception) {
                Log.d("ProfileViewModel", "Erreur lors du chargement des pages USER init : $e")
                RetrofitInstance.setNetworkAvailable(false)
            }
        }
    }

    private fun saveFollowingToPrefs() {
        val gson = Gson()
        val followingsJson = gson.toJson(_following.value)
        prefs.edit().putString("following", followingsJson).apply()
        viewModelScope.launch(exceptionHandler) {
            try {
                RetrofitInstance.api.save_user_data(
                    SQL().saveUserData(
                        username,
                        "followings",
                        "",
                        "",
                        "",
                        "",
                        followingsJson,
                        ""
                    )
                )
            } catch (e: Exception) {
                Log.d("ProfileViewModel", "Erreur lors de la sauvegarde des followings USER : $e")
                RetrofitInstance.setNetworkAvailable(false)
            }
        }
    }

    private fun getFollowingFromPrefs(usernamevisited: String = username) {
        val gson = Gson()
        val followingsJsonFromPref = prefs.getString("following", "")
        if (!followingsJsonFromPref.isNullOrEmpty()) {
            val type = object : TypeToken<List<String>>() {}.type
            _following.value =  gson.fromJson(followingsJsonFromPref, type)
        } else {
            var followingjson: String
            viewModelScope.launch(exceptionHandler) {
                try {
                    followingjson = RetrofitInstance.api.get_user_data(SQL().getUserData(usernamevisited, "followings"))

                    if (followingjson != "NULL") {
                        val type = object : TypeToken<List<String>>() {}.type
                        _following.value = gson.fromJson(followingjson, type)
                        prefs.edit().putString("following", followingjson).apply()
                    }

                } catch (e: Exception) {
                    Log.d("ProfileViewModel", "Erreur lors du chargement des followings USER : $e")
                    RetrofitInstance.setNetworkAvailable(false)
                }
            }

        }
    }

    private fun saveFollowersToPrefs() {
        val gson = Gson()
        val followersJson = gson.toJson(_followers.value)
        prefs.edit().putString("followers", followersJson).apply()
        viewModelScope.launch(exceptionHandler) {
            try {
                RetrofitInstance.api.save_user_data(
                    SQL().saveUserData(
                        username,
                        "followers",
                        "",
                        "",
                        "",
                        "",
                        "",
                        followersJson
                    )
                )
            } catch (e: Exception) {
                Log.d("ProfileViewModel", "Erreur lors de la sauvegarde des followers USER : $e")
                RetrofitInstance.setNetworkAvailable(false)
            }
        }
    }

    private fun getFollowersFromPrefs(usernamevisited: String = username) {
        val gson = Gson()
        val followersJsonFromPref = prefs.getString("followers", "")
        val type = object : TypeToken<List<String>>() {}.type
        if (!followersJsonFromPref.isNullOrEmpty()) {
            _followers.value = gson.fromJson(followersJsonFromPref, type)
        } else {

            Log.d("ProfileViewModel", "followersjsonfrompref : $followersJsonFromPref")
            var followersJson: String
            viewModelScope.launch(exceptionHandler) {
                try {
                    followersJson = RetrofitInstance.api.get_user_data(
                        SQL().getUserData(
                            usernamevisited,
                            "followers"
                        )
                    )
                    Log.d("ProfileViewModel", "followersjson : $followersJson")
                    if (followersJson != "NULL") {
                        val type = object : TypeToken<List<String>>() {}.type
                        _followers.value = gson.fromJson(followersJson, type)
                        Log.d("ProfileViewModel", "followers : ${_followers.value}")
                        prefs.edit().putString("followers", followersJson).apply()
                    }

                } catch (e: Exception) {
                    Log.d("ProfileViewModel", "Erreur lors du chargement des followers USER : $e")

                    RetrofitInstance.setNetworkAvailable(false)
                }
            }
        }

/*
        if (!followersJsonFromPref.isNullOrEmpty()) {
            val type = object : TypeToken<List<String>>() {}.type
            _followers.value =  gson.fromJson(followersJsonFromPref, type)
        } else {
            var followersJson: String
            viewModelScope.launch(exceptionHandler) {
                try {
                    followersJson = RetrofitInstance.api.get_user_data(SQL().getUserData(usernamevisited, "followers"))
                    Log.d("ProfileViewModel", "followersjson : $followersJson")
                    if (followersJson != "NULL") {
                        val type = object : TypeToken<List<String>>() {}.type
                        _followers.value = gson.fromJson(followersJson, type)
                        Log.d("ProfileViewModel", "followers : ${_followers.value}")
                        prefs.edit().putString("followers", followersJson).apply()
                    }

                } catch (e: Exception) {
                    Log.d("ProfileViewModel", "Erreur lors du chargement des followers USER : $e")
                    RetrofitInstance.setNetworkAvailable(false)
                }
            }

        }

 */
    }




    private fun saveBioToPrefs() {
        prefs.edit().putString("bio", _biography.value).apply()
        viewModelScope.launch(exceptionHandler) {
            try {
                RetrofitInstance.api.save_user_data(
                    SQL().saveUserData(
                        username,
                        "biography",
                        "",
                        "",
                        _biography.value,
                        "",
                        "",
                        ""
                    )
                )
            } catch (e: Exception) {
                Log.d("ProfileViewModel", "Erreur lors de la sauvegarde de la bio USER : $e")
                RetrofitInstance.setNetworkAvailable(false)
            }
        }
    }

    private fun getBioFromPrefs(usernamevisited: String = username) {
        val bio = prefs.getString("bio", "")
        if (!bio.isNullOrEmpty()) {
            _biography.value =  bio
        } else {
            var biofrompref: String
            viewModelScope.launch(exceptionHandler) {
                try {
                    biofrompref = RetrofitInstance.api.get_user_data(SQL().getUserData(usernamevisited, "biography"))

                    if (biofrompref != "NULL") {
                        _biography.value = biofrompref
                        prefs.edit().putString("bio", biofrompref).apply()
                    }

                } catch (e: Exception) {
                    Log.d("ProfileViewModel", "Erreur lors du chargement de la bio USER : $e")
                    RetrofitInstance.setNetworkAvailable(false)
                }
            }

        }
    }


    private fun saveNameToPrefs() {
        prefs.edit().putString("name", _name.value).apply()
        viewModelScope.launch(exceptionHandler) {
            try {
                RetrofitInstance.api.save_user_data(
                    SQL().saveUserData(
                        username,
                        "customname",
                        "",
                        "",
                        "",
                        _name.value,
                        "",
                        ""
                    )
                )
            } catch (e: Exception) {
                Log.d("ProfileViewModel", "Erreur lors de la sauvegarde du name USER : $e")
                RetrofitInstance.setNetworkAvailable(false)
            }
        }
    }

    private fun getNameFromPrefs(usernamevisited: String = username) {
        val name = prefs.getString("name", "")
        if (!name.isNullOrEmpty()) {
            _name.value =  name
        } else {
            var namefrompref: String
            viewModelScope.launch(exceptionHandler) {
                try {
                    namefrompref = RetrofitInstance.api.get_user_data(SQL().getUserData(usernamevisited, "customname"))

                    if (namefrompref != "NULL") {
                        _name.value = namefrompref
                        prefs.edit().putString("name", namefrompref).apply()
                    }

                } catch (e: Exception) {
                    Log.d("ProfileViewModel", "Erreur lors du chargement du name USER : $e")
                    RetrofitInstance.setNetworkAvailable(false)
                }
            }

        }
    }


    private fun saveUserPagesToPrefs() {
        val gson = Gson()
        val pagesJson = gson.toJson(_userPages.value)
        prefs.edit().putString("userPages", pagesJson).apply()
        viewModelScope.launch(exceptionHandler) {
            try {
                RetrofitInstance.api.save_user_data(
                    SQL().saveUserData(
                        username,
                        "userposts",
                        pagesJson,
                        "",
                        "",
                        "",
                        "",
                        ""
                    )
                )
            } catch (e: Exception) {
                Log.d("ProfileViewModel", "Erreur lors de la sauvegarde des pages USER : $e")
                RetrofitInstance.setNetworkAvailable(false)
            }
        }
        Log.d("ProfileViewModel", "User pages : ${_userPages.value}")
    }

    private fun getUserPagesFromPrefs(usernamevisited: String = username) {
        val gson = Gson()
        val pagesJson = prefs.getString("userPages", "")
        Log.d("ProfileViewModel", "User pages json : $pagesJson")
        if (!pagesJson.isNullOrEmpty()) {
            val type = object : TypeToken<List<List<String>>>() {}.type
            Log.d("ProfileViewModel", "User pages from pref : ${_userPages}")
            _userPages.value =  gson.fromJson(pagesJson, type)
        } else {
            var userjson: String
            viewModelScope.launch(exceptionHandler) {
                try {
                    userjson = RetrofitInstance.api.get_user_data(SQL().getUserData(usernamevisited, "userposts"))
                    Log.d("ProfileViewModel", "User pages json : $userjson")

                    if (userjson != "NULL") {
                        val type = object : TypeToken<List<List<String>>>() {}.type
                        _userPages.value = gson.fromJson(userjson, type)
                        prefs.edit().putString("userPages", userjson).apply()
                    }

                } catch (e: Exception) {
                    Log.d("ProfileViewModel", "Erreur lors du chargement des pages USER : $e")
                    RetrofitInstance.setNetworkAvailable(false)
                }
            }

        }
    }

    fun getUserPages(usernamevisited: String = username): List<List<String>> {
        getUserPagesFromPrefs(usernamevisited)
        return _userPages.value
    }


    fun addToUserPages(newPage: List<String>) {
        getUserPagesFromPrefs()
        _userPages.value = _userPages.value.toMutableList().apply { add(newPage) }
        saveUserPagesToPrefs()
    }

    fun removeFromUserPages(newPage: List<String>) {
        getUserPagesFromPrefs()
        _userPages.value = _userPages.value.toMutableList().apply { remove(newPage) }
        saveUserPagesToPrefs()
    }






    private fun saveSavedPagesToPrefs() {
        val gson = Gson()
        val pagesJson = gson.toJson(_savedPages.value)
        prefs.edit().putString("savedPages", pagesJson).apply()
        viewModelScope.launch(exceptionHandler) {
            try {
                RetrofitInstance.api.save_user_data(
                    SQL().saveUserData(
                        username,
                        "savedposts",
                        "",
                        pagesJson,
                        "",
                        "",
                        "",
                        ""
                    )
                )
            } catch (e: Exception) {
                Log.d("ProfileViewModel", "Erreur lors de la sauvegarde des pages USER, saved : $e")
                RetrofitInstance.setNetworkAvailable(false)
            }
        }
        Log.d("ProfileViewModel", "User pages : ${_userPages.value}")
    }

    private fun getSavedPagesFromPrefs() {
        val gson = Gson()
        val pagesJson = prefs.getString("savedPages", "")
        Log.d("ProfileViewModel", "saved pages json : $pagesJson")
        if (!pagesJson.isNullOrEmpty()) {
            val type = object : TypeToken<List<List<String>>>() {}.type
            _savedPages.value = gson.fromJson(pagesJson, type)
        } else {
            var savedjson: String
            viewModelScope.launch(exceptionHandler) {
                try {
                    savedjson = RetrofitInstance.api.get_user_data(SQL().getUserData(username, "savedposts"))

                    if (savedjson != "NULL") {
                        val type = object : TypeToken<List<List<String>>>() {}.type
                        _savedPages.value = gson.fromJson(savedjson, type)
                        prefs.edit().putString("savedPages", savedjson).apply()

                    }

                } catch (e: Exception) {
                    Log.d("ProfileViewModel", "Erreur lors du chargement des pages USER, saved : $e")
                    RetrofitInstance.setNetworkAvailable(false)
                }
            }

        }
    }

    fun getSavedPages(): List<List<String>> {
        getSavedPagesFromPrefs()
        return _savedPages.value
    }



    fun addToSavedPages(newPage: List<String>) {
        getSavedPagesFromPrefs()
        _savedPages.value = _savedPages.value.toMutableList().apply { add(newPage) }
        saveSavedPagesToPrefs()
    }

    fun removeFromSavedPages(newPage: List<String>) {
        getSavedPagesFromPrefs()
        _savedPages.value = _savedPages.value.toMutableList().apply { remove(newPage) }
        saveSavedPagesToPrefs()
    }

    private val _goSettings = MutableStateFlow<Boolean>(false)
    val goSettings: StateFlow<Boolean> = _goSettings

    private val _indexPage = MutableStateFlow<Int>(0)
    val indexPage: StateFlow<Int> = _indexPage



    private val _profileLazy = MutableStateFlow<Boolean>(false)
    val profileLazy: StateFlow<Boolean> = _profileLazy

    fun setProfileLazy(value: Boolean) {
        _profileLazy.value = value
    }

    private val _currentPref = MutableStateFlow<String>("")
    val currentPref: StateFlow<String> = _currentPref

    fun setCurrentPref(value: String) {
        _currentPref.value = value
    }



    fun loadProfileImage(prefs: SharedPreferences, username: String) {
        viewModelScope.launch(exceptionHandler) {
            val image = getProfileImage(prefs, username)
            _profilePic.value = image
        }
    }

    fun setProfilePic(value: Bitmap?) {
        _profilePic.value = value
    }

    fun setIndexPage(value: Int) {
        _indexPage.value = value
    }

    private val _editProfile = MutableStateFlow<Boolean>(false)
    val editProfile: StateFlow<Boolean> = _editProfile

    fun toggleEditProfile() {
        _editProfile.value = !_editProfile.value
    }

    fun toggleGoSettings() {
        _goSettings.value = !_goSettings.value
    }

    fun setGoSettings(value: Boolean) {
        _goSettings.value = value

    }


    private val _audioPrompt = MutableStateFlow<String>("Voice Affect: Calm, composed, and reassuring; project quiet authority and confidence.\n" +
            "\n" +
            "Tone: Sincere, empathetic, and gently authoritative—express genuine apology while conveying competence.\n" +
            "\n" +
            "Pacing: Steady and moderate; unhurried enough to communicate care, yet efficient enough to demonstrate professionalism.\n" +
            "\n" +
            "Emotion: Genuine empathy and understanding; speak with warmth, especially during apologies (\"I'm very sorry for any disruption...\").\n" +
            "\n" +
            "Pronunciation: Clear and precise, emphasizing key reassurances (\"smoothly,\" \"quickly,\" \"promptly\") to reinforce confidence.\n" +
            "\n" +
            "Pauses: Brief pauses after offering assistance or requesting details, highlighting willingness to listen and support.")
    val audioPrompt: StateFlow<String> = _audioPrompt

    fun setAudioPrompt(value: String) {
        _audioPrompt.value = value
    }

    private val _voice = MutableStateFlow<String>("alloy")
    val voice: StateFlow<String> = _voice

    fun setVoice(value: String) {
        _voice.value = value
    }

    private val _audioModel = MutableStateFlow<String>("ChatGPT 4o TTS")
    val audioModel: StateFlow<String> = _audioModel

    fun setAudioModel(value: String) {
        _audioModel.value = value
    }



}


class ProfileViewModelFactory(private val context: Context, private val username: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(context, username) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}