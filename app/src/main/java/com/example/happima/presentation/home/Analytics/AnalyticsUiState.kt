package com.example.happima.presentation.home.Analytics

import com.example.happima.presentation.home.MoodScale.moodDataDb

data class AnalyticsUiState(val moodPoints: List<moodDataDb>, val showGraph : Boolean)
