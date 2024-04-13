package com.example.happima.presentation.database

import com.example.happima.presentation.Community.userMessage
import com.example.happima.presentation.home.MoodScale.moodDataDb
import com.example.happima.presentation.sign_in.UserData

interface Repository {
    fun getUserData(callback: (UserData) -> Unit)
    fun getMood(callback:(moodDataDb?) -> Unit)
    fun addMood(moodData: moodDataDb)
    fun postMessage(content: String)
    fun postReply(content: String, userMessageRepliedTo: userMessage)
    fun getFeed(callback: (List<userMessage>) -> Unit)
    fun fetchMoodGraph(callback: (List<moodDataDb>) -> Unit)
}