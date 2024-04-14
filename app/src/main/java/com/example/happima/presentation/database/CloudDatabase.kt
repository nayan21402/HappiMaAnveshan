package com.example.happima.presentation.database

import android.content.ContentValues.TAG
import android.util.Log
import com.example.happima.presentation.Community.userMessage
import com.example.happima.presentation.sign_in.UserData
import com.example.happima.presentation.home.MoodScale.moodDataDb
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class CloudDatabase {
    private val db = Firebase.firestore
    private val surveyResult = mutableListOf<String>()

    fun getSurveyResult(callback: (List<String>) -> Unit){

    }
    fun addMessage(userData: UserData,content : String){
        val userMessage= userMessage(content = content, userData = userData, date = Date())
        Log.d("comm", "here")

        db.collection("Community")
            .document(userMessage.id)
            .set(userMessage)
            .addOnSuccessListener {
                Log.d("comm", "Doc added to comm")
            }
            .addOnFailureListener { e ->
                Log.e("comm", "Error adding doc to comm",e)
            }

    }

    fun addMessageReply(userData: UserData,userMessageRepliedTo: userMessage,content : String){
        val updatedReplyList = userMessageRepliedTo.replyList.toMutableList().apply {
            add(userMessage(content = content, userData = userData, date = Date()))
        }

        val userMessageUpdated = userMessage(
            id = userMessageRepliedTo.id,
            content = userMessageRepliedTo.content,
            userData = userMessageRepliedTo.userData,
            date = userMessageRepliedTo.date,
            replyList = updatedReplyList
        )

        Log.d("comm", "here")

        db.collection("Community")
            .document(userMessageRepliedTo.id)
            .set(userMessageUpdated)
            .addOnSuccessListener {
                Log.d("comm", "Reply added to message")
            }
            .addOnFailureListener { e ->
                Log.e("comm", "Error adding reply",e)
            }
    }

    fun retrieveMessage(callback: (List<userMessage>) -> Unit){
        db.collection("Community")
            .limit(10)
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                querySnapshot->
                    var tempList = mutableListOf<userMessage>()
                    querySnapshot.documents.forEachIndexed { index, documentSnapshot ->
                        documentSnapshot.toObject<userMessage>()?.let { tempList.add(it) }
                    }
                callback(tempList)
                Log.d("commR",tempList.toString())
            }
            .addOnFailureListener{e ->
                Log.e("commR", "Error fetching",e)
            }
    }

    fun addMoodDb(moodData: moodDataDb, userData: UserData?){
        if (userData != null) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val dateStr = moodData.date?.let { dateFormat.format(it) }

            if (dateStr != null) {
                val collectionName = "users/${userData.userId}/moodData"

                db.collection(collectionName)
                    .document(dateStr)
                    .set(moodData)
                    .addOnSuccessListener {
                        Log.d(TAG, "DocumentSnapshot added")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
            }
        }

    }


    fun fetchDb(userData: UserData?, callback: (moodDataDb?) -> Unit) {
        if (userData != null) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val dateStr = dateFormat.format(Date())
            Log.d("userID",userData.userId.toString())
            db.collection("users")
                .document(userData.userId)
                .collection("moodData")
                .document(dateStr)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val moodData = documentSnapshot.toObject(moodDataDb::class.java)
                        callback(moodData)
                    } else {
                        Log.d(TAG, "No such document")
                        callback(null)
                    }
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error fetching document", e)
                    callback(null)
                }
        } else {
            Log.w(TAG, "UserData is null")
            callback(null)
        }
    }
    fun fetchLast15DataPoints(userData: UserData?, callback: (List<moodDataDb>) -> Unit) {
        if (userData != null) {
            db.collection("users")
                .document(userData.userId)
                .collection("moodData")
                .orderBy("date", Query.Direction.DESCENDING) // Order documents by date in descending order
                .limit(15) // Limit to fetch only the last 15 documents
                .get()
                .addOnSuccessListener { querySnapshot ->

                    val dataPoints = mutableListOf<moodDataDb>()
                    for (document in querySnapshot.documents) {
                        val moodData = document.toObject(moodDataDb::class.java)
                        moodData?.let {
                            dataPoints.add(it)
                        }
                    }
                    Log.d("graph0",dataPoints.toString())

                    callback(dataPoints)

                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error fetching documents", e)
                    callback(emptyList()) // Return an empty list if there's an error
                }
        } else {
            Log.w(TAG, "UserData is null")
            callback(emptyList()) // Return an empty list if UserData is null
        }
    }


    fun addDummyMoodData(userData: UserData?) {
        if (userData != null) {
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            for (i in 0 until 15) {
                calendar.add(Calendar.DAY_OF_YEAR, -1) // Move back one day
                val date = calendar.time
                val mood = (0..4).random() // Random mood value between 1 and 5

                val moodData = moodDataDb(date, mood)
                val dateStr = dateFormat.format(date)
                val collectionName = "users/${userData.userId}/moodData"

                db.collection(collectionName)
                    .document(dateStr)
                    .set(moodData)
                    .addOnSuccessListener {
                        Log.d(TAG, "Dummy data added for $dateStr")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding dummy data for $dateStr", e)
                    }
            }
        }
    }







}