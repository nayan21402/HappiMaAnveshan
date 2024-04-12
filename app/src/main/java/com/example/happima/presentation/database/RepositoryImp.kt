package com.example.happima.presentation.database

import com.example.happima.presentation.Community.userMessage
import com.example.happima.presentation.home.MoodScale.moodDataDb
import com.plcoding.composegooglesignincleanarchitecture.presentation.sign_in.GoogleAuthUiClient

class RepositoryImp(private val googleAuthUiClient: GoogleAuthUiClient) : Repository {
    private var db = CloudDatabase()

    override fun getMood(callback: (moodDataDb?) -> Unit) {
        val user = googleAuthUiClient.getSignedInUser()
        db.fetchDb(user) {
            callback(it)
        }
    }

    override fun addMood(moodData: moodDataDb) {
        val user = googleAuthUiClient.getSignedInUser()
        db.addMoodDb(moodData, user)
    }

    override fun postMessage(content: String) {
        val user = googleAuthUiClient.getSignedInUser()
        if (user != null) {
            db.addMessage(user, content)
        }
    }

    override fun getFeed(callback: (List<userMessage>) -> Unit) {
        db.retrieveMessage {
            callback(it)
        }
    }
}