package com.example.happima.presentation.home.MoodScale

import com.example.happima.LoadedResource.Resource
import com.example.happima.LoadedResource.moodDataUi
import com.google.firebase.firestore.PropertyName
import java.util.Date

data class moodDataDb(
    @PropertyName("date") var date: Date? = null, // Ensure names match Firestore document
    @PropertyName("mood") var mood: Int? = null
)

data class MoodUiState(
    val moodList: List<moodDataUi> = Resource.provideMoodList(),
    val selectedMood : Int =2,
    val userName : String = "",
)