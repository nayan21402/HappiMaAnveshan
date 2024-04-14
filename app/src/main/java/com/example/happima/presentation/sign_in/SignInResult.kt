package com.example.happima.presentation.sign_in

import com.example.happima.presentation.home.MoodScale.moodDataDb

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?,
    val newUser: Boolean = true
)

data class UserData(
    val userId: String,
    val username: String?,
    val profilePictureUrl: String?,
    var mood: moodDataDb? = null
)
 {
    // Default constructor
    constructor() : this("", null, null,null)
}
