package com.example.happima

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import com.example.happima.presentation.Community.CommunityUi
import com.example.happima.presentation.Community.CommunityViewModel
import com.example.happima.presentation.Gemini.ChatViewModel
import com.example.happima.presentation.Survey.SurveyScreen
import com.example.happima.presentation.home.LandingScreen
import com.example.happima.presentation.SettingScreen
import com.example.happima.presentation.Survey.SurveyConsent
import com.google.android.gms.auth.api.identity.Identity
import com.plcoding.composegooglesignincleanarchitecture.presentation.sign_in.GoogleAuthUiClient
import com.example.happima.presentation.sign_in.SignInScreen
import com.example.happima.presentation.sign_in.SignInViewModel
import com.example.happima.presentation.database.CloudDatabase
import com.example.happima.presentation.database.RepositoryImp
import com.example.happima.presentation.home.HomeViewModel
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.sample.feature.chat.ChatScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext))
    }

    lateinit var homeViewModel: HomeViewModel
    lateinit var chatViewModel: ChatViewModel
    lateinit var signInViewModel: SignInViewModel
    lateinit var communityViewModel: CommunityViewModel
    lateinit var repository: RepositoryImp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository=RepositoryImp(googleAuthUiClient)
        signInViewModel = SignInViewModel()
        chatViewModel=ChatViewModel(GenerativeModel(
            modelName = "gemini-1.0-pro",
            apiKey = "AIzaSyC4lbtQhwQtNhGOJoFrDrQp66gWbARUXAk"))

        setContent {
            AppTheme{

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                        val navController = rememberNavController()
                        NavHost(navController = navController, startDestination = "signUp") {
                            composable("signUp") {
                                val signInState by signInViewModel.state.collectAsState()

                                LaunchedEffect(key1 = Unit) {
                                    if(googleAuthUiClient.getSignedInUser() != null) {
                                        homeViewModel= HomeViewModel(repository)

                                        navController.navigate("home"){
                                            popUpTo(route = "signUp"){
                                                inclusive=true
                                            }
                                        }
                                    }
                                }

                                val launcher = rememberLauncherForActivityResult(
                                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                                    onResult = { result ->
                                        if(result.resultCode == RESULT_OK) {
                                            lifecycleScope.launch {
                                                val signInResult = googleAuthUiClient.signInWithIntent(
                                                    intent = result.data ?: return@launch
                                                )
                                                signInViewModel.onSignInResult(signInResult)
                                                homeViewModel= signInResult.data?.let { it1 ->
                                                    HomeViewModel(
                                                        it1
                                                    )
                                                }!!

                                            }
                                        }
                                    }
                                )

                                LaunchedEffect(key1 = signInState.isSignInSuccessful) {
                                    if(signInState.isSignInSuccessful) {
                                        Toast.makeText(
                                            applicationContext,
                                            "Sign in successful",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        signInViewModel.resetState()
                                        homeViewModel= HomeViewModel(repository)
                                        //change this to surveyConsent
                                        navController.navigate("home"){
                                            popUpTo(route = "signUp"){
                                                inclusive=true
                                            }
                                        }


                                    }
                                }

                                SignInScreen(
                                    state = signInState,
                                    onSignInClick = {
                                        lifecycleScope.launch {
                                            val signInIntentSender = googleAuthUiClient.signIn()
                                            launcher.launch(
                                                IntentSenderRequest.Builder(
                                                    signInIntentSender ?: return@launch
                                                ).build()
                                            )
                                        }
                                    }
                                )
                            }
                            composable("home") {

                                HomeScreen(
                                    navController=navController,
                                    homeViewModel = homeViewModel
                                )
                            }
                            composable("setting") {
                                SettingScreen(
                                    userData = googleAuthUiClient.getSignedInUser(),
                                    navController = navController, homeViewModel = homeViewModel)
                                {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()
                                        Toast.makeText(
                                            applicationContext,
                                            "Signed out",
                                            Toast.LENGTH_LONG
                                        ).show()

                                        navController.navigate(route="signUp"){
                                            popUpTo(0){
                                                inclusive=true
                                            }
                                        }

                                    }
                                }
                            }
                            
                            composable("surveyConsent"){
                                SurveyConsent(navController = navController)
                            }
                            
                            composable("survey"){
                                SurveyScreen(navController =navController)
                            }

                            composable("chatBot"){

                                ChatScreen(chatViewModel = chatViewModel, homeViewModel = homeViewModel, navController = navController)
                            }

                            composable("community"){
                                communityViewModel=CommunityViewModel(userData = googleAuthUiClient.getSignedInUser())
                                CommunityUi(
                                    communityViewModel = communityViewModel,
                                    homeViewModel = homeViewModel,
                                    navController = navController
                                )
                            }
                            
                        }
                    }

            }
        }
    }
}

