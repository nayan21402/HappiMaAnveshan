package com.example.happima.presentation.Community

import com.example.happima.presentation.sign_in.UserData
import com.google.firebase.firestore.PropertyName
import java.util.Date
import java.util.UUID

data class userMessage(
    val id: String= UUID.randomUUID().toString(),
    val content: String = "",
    val userData: UserData? = null,
    val date: Date? = null,
    val replyList : MutableList<userMessage> = arrayListOf()
) {
    // Default constructor
    constructor() : this("","", null, null)
}

