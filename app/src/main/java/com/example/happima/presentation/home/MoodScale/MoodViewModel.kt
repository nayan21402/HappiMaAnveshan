package com.example.happima.presentation.home.MoodScale

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happima.presentation.database.RepositoryImp
import com.example.happima.presentation.home.Analytics.AnalyticsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

class MoodViewModel(val repository: RepositoryImp) :ViewModel() {
    private val _uiState = MutableStateFlow(MoodUiState())
    val uiState =_uiState.asStateFlow()

    fun updateSelectedMood(mood : Int){
        _uiState.value=_uiState.value.copy(selectedMood = mood)
    }

    fun addMoodDb(){
        viewModelScope.launch {
            repository.addMood(moodDataDb(date = Date(), mood = _uiState.value.selectedMood))
        }
    }
}