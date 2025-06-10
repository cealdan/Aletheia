package com.example.aletheia.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EvalAiContentModel : ViewModel() {

    private val _responses = MutableStateFlow<List<String>>(listOf())
    val responses: StateFlow<List<String>> = _responses

    fun addResponse(response: String) {
        _responses.value += response
        Log.d("EvalAiContentModel", "Nouvelle réponse ajoutée : $response")
    }

    fun getResponses(): List<String> {
        Log.d("EvalAiContentModel", "Récupération des réponses : ${_responses.value}")
        return _responses.value

    }
}