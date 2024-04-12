package com.example.happima.presentation.home

import com.example.happima.presentation.sign_in.UserData
import kotlin.random.Random


data class homeUiState(val tip: Int = Random.nextInt(0, 50), val showMoodDialog : Boolean = false, val currentMood : Int? = null,  val currentScreen : String = "Home")
