package com.example.happima.presentation.home.Analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happima.presentation.database.Repository
import com.example.happima.presentation.home.MoodScale.moodDataDb
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnalyticsViewModel(private val repository: Repository) : ViewModel() {
    private val _uiState = MutableStateFlow(AnalyticsUiState(emptyList(), false))
    val uiState =_uiState.asStateFlow()

    fun fetchMoodPoints(){
        viewModelScope.launch{
            repository.fetchMoodGraph { respone ->
                _uiState.value=_uiState.value.copy(moodPoints = respone, showGraph = true)
            }
        }
    }
}