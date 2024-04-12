package com.example.happima.presentation.Community

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happima.presentation.database.Repository
import com.example.happima.presentation.sign_in.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CommunityViewModel(
    private val repository: Repository,
    userData: UserData?
) : ViewModel() {
    private var _uiState = MutableStateFlow(CommunityUiState())
    val uiState = _uiState.asStateFlow()

    fun updateInput(newInput: String) {
        _uiState.value = _uiState.value.copy(input = newInput)
    }

    fun updateFeed() {
        viewModelScope.launch {
            repository.getFeed { messages ->
                _uiState.value = _uiState.value.copy(feed = messages, feedUpdated = true)
                Log.d("commFromViewInside", _uiState.value.feedUpdated.toString())
            }
        }
        Log.d("commFromView", _uiState.value.feedUpdated.toString())
    }

    fun addMessage() {
            viewModelScope.launch {
                repository.postMessage(_uiState.value.input)
                _uiState.value = _uiState.value.copy(feedUpdated = false)
            }

    }
}
