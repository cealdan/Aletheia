package com.example.aletheia.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GlobalViewModel(context: Context, username: String) : ViewModel() {

    val sharedPref = context.getSharedPreferences("globalviewmodel::$username", Context.MODE_PRIVATE)

    val firstTimeCreationPageFromPrefs = sharedPref.getBoolean("firstTimeCreationPage", true)
    private val _firstTimeCreationPage = MutableStateFlow<Boolean>(firstTimeCreationPageFromPrefs)
    val firstTimeCreationPage: StateFlow<Boolean> = _firstTimeCreationPage
    fun setFirstTimeCreationPage(value: Boolean) {
        _firstTimeCreationPage.value = value
        sharedPref.edit().putBoolean("firstTimeCreationPage", value).apply()
    }

    private val _whichPage = MutableStateFlow<String>("")
    val whichPage: StateFlow<String> = _whichPage

    val userprefs = context.getSharedPreferences(username, Context.MODE_PRIVATE)

    val hapticpref_FROMPREF = userprefs.getBoolean("hapticpref", true)

    private val _hapticPref = MutableStateFlow<Boolean>(hapticpref_FROMPREF)
    val hapticPref: StateFlow<Boolean> = _hapticPref

    fun toggleHapticPref() {
        _hapticPref.value = !_hapticPref.value
        userprefs.edit().putBoolean("hapticpref", _hapticPref.value).apply()
    }

    fun setWhichPage(value: String) {
        _whichPage.value = value
    }

    private val _drawerState = MutableStateFlow<DrawerState>(DrawerState(DrawerValue.Closed))
    val drawerState: MutableStateFlow<DrawerState> = _drawerState


    val advicefromAIPref_FROMPREF = userprefs.getBoolean("advicefromAIPref", true)
    val customPromptToAI_FROMPREF = userprefs.getString("customPromptToAI", "Writing cool prompts to generate good AI content")


    private val _adviceFromAIPref = MutableStateFlow<Boolean>(advicefromAIPref_FROMPREF)
    val adviceFromAIPref: StateFlow<Boolean> = _adviceFromAIPref

    fun toggleAdviceFromAIPref() {
        _adviceFromAIPref.value = !_adviceFromAIPref.value
        userprefs.edit().putBoolean("advicefromAIPref", _adviceFromAIPref.value).apply()
    }

    private val _customPromptToAI = MutableStateFlow<String>(customPromptToAI_FROMPREF!!)
    val customPromptToAI: StateFlow<String> = _customPromptToAI

    fun setCustomPromptToAI(value: String) {
        _customPromptToAI.value = value
        userprefs.edit().putString("customPromptToAI", value).apply()

    }

    val assistantModel_FROMPREF = userprefs.getString("assistantModel", "Meta Llama 3.1")

    private val _assistantModel = MutableStateFlow<String>(assistantModel_FROMPREF!!)
    val assistantModel: StateFlow<String> = _assistantModel

    fun setAssistantModel(value: String) {
        _assistantModel.value = value
        userprefs.edit().putString("assistantModel", value).apply()
    }




}



class GlobalViewModelFactory(private val context: Context, private val username: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GlobalViewModel::class.java)) {
            return GlobalViewModel(context, username) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}