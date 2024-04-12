package com.example.happima.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happima.presentation.database.CloudDatabase
import com.example.happima.presentation.database.RepositoryImp
import com.example.happima.presentation.sign_in.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class HomeViewModel(val repository: RepositoryImp): ViewModel() {
    private val _homeUiState = MutableStateFlow(homeUiState())
    val homeUiState : StateFlow<homeUiState> = _homeUiState.asStateFlow()

    fun updateMoodFb(){
        viewModelScope.launch {
            repository.getMood { moodData ->
                val mood = moodData?.mood
                _homeUiState.value = _homeUiState.value.copy(currentMood = mood)
                Log.d("homeUi", mood.toString())
                if (mood == null) showMoodDialog(true)
            }
        }

    }

    fun updateTip(){
        _homeUiState.value=_homeUiState.value.copy(tip = Random.nextInt(0, 50))
    }

    fun updateCurrentScreen(screen : String){
        _homeUiState.value=_homeUiState.value.copy(currentScreen = screen)
    }

    fun showMoodDialog(change :Boolean){
        _homeUiState.value=_homeUiState.value.copy(showMoodDialog = change)
    }

    init {
        updateMoodFb() // update mood on initialisation

    }


}