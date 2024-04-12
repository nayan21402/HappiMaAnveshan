package com.example.happima.presentation.home.MoodScale

import com.google.firebase.firestore.PropertyName
import java.util.Date

data class moodDataDb(
    @PropertyName("date") var date: Date? = null, // Ensure names match Firestore document
    @PropertyName("mood") var mood: Int? = null
)