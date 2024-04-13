package com.example.happima.presentation.database

import com.example.happima.presentation.Community.userMessage
import com.example.happima.presentation.home.MoodScale.moodDataDb
import com.example.happima.presentation.sign_in.UserData
import com.plcoding.composegooglesignincleanarchitecture.presentation.sign_in.GoogleAuthUiClient

class RepositoryImp(private val googleAuthUiClient: GoogleAuthUiClient) : Repository {
    private var db = CloudDatabase()
    override fun getUserData(callback: (UserData) -> Unit) {
        val user = googleAuthUiClient.getSignedInUser()
        if (user != null) {
            callback(user)
        }
    }

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


    override fun postReply(content: String, userMessageRepliedTo: userMessage) {
        val user = googleAuthUiClient.getSignedInUser()
        if (user != null) {
            db.addMessageReply(userData=user,userMessageRepliedTo=userMessageRepliedTo,content=content)
        }
    }


    override fun getFeed(callback: (List<userMessage>) -> Unit) {
        db.retrieveMessage {
            callback(it)
        }
    }

    override fun fetchMoodGraph(callback: (List<moodDataDb>) -> Unit) {
        db.fetchLast15DataPoints(googleAuthUiClient.getSignedInUser()){
            callback(it)
        }
    }
}