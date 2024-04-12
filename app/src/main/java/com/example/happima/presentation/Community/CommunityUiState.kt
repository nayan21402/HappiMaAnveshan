package com.example.happima.presentation.Community

import com.example.happima.presentation.sign_in.UserData

data class CommunityUiState(val input:String = "hmm",val feed : List<userMessage> = emptyList(), val feedUpdated: Boolean = false, val messageUploaded: Boolean = false)