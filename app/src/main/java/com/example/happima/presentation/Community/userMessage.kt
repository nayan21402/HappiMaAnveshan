package com.example.happima.presentation.Community

import com.example.happima.presentation.sign_in.UserData
import com.google.firebase.firestore.PropertyName
import java.util.Date

data class userMessage(
    val content: String = "",
    val userData: UserData? = null,
    val date: Date? = null
) {
    // Default constructor
    constructor() : this("", null, null)
}

