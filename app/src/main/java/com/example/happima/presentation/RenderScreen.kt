package com.example.happima.presentation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.happima.presentation.database.RepositoryImp
import com.example.happima.presentation.navigationBars.BottomBar
import com.example.happima.presentation.navigationBars.TopBar
import com.example.happima.presentation.sign_in.UserData
import com.example.happima.presentation.home.HomeViewModel

@Composable
fun RenderScreen(repository: RepositoryImp, enableTopBar : Boolean = true,homeViewModel: HomeViewModel,navController: NavController,content :@Composable () -> Unit){

    Scaffold(topBar = { if(enableTopBar)TopBar(repository = repository,homeViewModel, navController = navController) else {} }, bottomBar = { BottomBar(homeViewModel = homeViewModel,navController = navController)}) {
        Box(
            modifier = Modifier
                .fillMaxSize() // Fill the maximum available size
                .padding(it) // Apply the external padding

        ) {
            content()
        }
    }
}