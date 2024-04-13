package com.example.happima.presentation.Community

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happima.presentation.database.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CommunityViewModel(
    private val repository: Repository
) : ViewModel() {
    private var _uiState = MutableStateFlow(CommunityUiState())
    val uiState = _uiState.asStateFlow()

    fun updateInput(newInput: String) {
        _uiState.value = _uiState.value.copy(input = newInput)
    }
    fun updateReplyInput(newInput: String) {
        _uiState.value = _uiState.value.copy(replyInput =newInput)
    }

    fun updateFeed() {
        viewModelScope.launch {
            repository.getFeed { messages ->
                _uiState.value = _uiState.value.copy(feed = messages, feedUpdated = true)
                Log.d("NewcommFromViewInside", _uiState.value.feedUpdated.toString())
            }
        }
        Log.d("NewcommFromView", _uiState.value.feedUpdated.toString())
    }

    fun addMessage() {
            viewModelScope.launch {
                repository.postMessage(_uiState.value.input)
                _uiState.value = _uiState.value.copy(feedUpdated = false)
            }

    }

    fun addReply(userMessage: userMessage) {
        viewModelScope.launch {
            repository.postReply(content = _uiState.value.replyInput, userMessageRepliedTo = userMessage)
            _uiState.value = _uiState.value.copy(feedUpdated = false)
        }

    }
}
