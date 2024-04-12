package com.example.happima.presentation.database

import com.example.happima.presentation.Community.userMessage
import com.example.happima.presentation.home.MoodScale.moodDataDb

interface Repository {
    fun getMood(callback:(moodDataDb?) -> Unit)
    fun addMood(moodData: moodDataDb)
    fun postMessage(content: String)
    fun getFeed(callback: (List<userMessage>) -> Unit)
}