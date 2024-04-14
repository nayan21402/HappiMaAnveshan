package com.example.happima.presentation.navigationBars

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.happima.R
import com.example.happima.presentation.home.HomeViewModel

import kotlinx.coroutines.selects.select


@Composable
fun BottomBar(navController: NavController, homeViewModel: HomeViewModel){
    val navBarHeight = WindowInsets.statusBars.asPaddingValues().calculateBottomPadding()

    val current = homeViewModel.homeUiState.collectAsState().value.currentScreen
    val navList = listOf("Communities","Home", "Health","Chatbot")
    Log.d("nav",current)
    NavigationBar(modifier = Modifier.padding(bottom = navBarHeight)) {
        NavigationBarItem(selected = navList[0]==current, onClick = {
            homeViewModel.updateCurrentScreen(navList[0])
            navController.navigate("community")

        },
            icon = {
                Icon(painterResource(id = R.drawable.group),"communities", tint = MaterialTheme.colorScheme.primary)
            }
        )
        NavigationBarItem(selected = navList[1]==current, onClick = {
            homeViewModel.updateCurrentScreen(navList[1])
            navController.navigate("home")
        },
            icon = {
                Icon(Icons.Filled.Home,"home", tint = MaterialTheme.colorScheme.primary)
            }
        )

        NavigationBarItem(selected = navList[2]==current, onClick = {
            homeViewModel.updateCurrentScreen(navList[2])
            navController.navigate("help")
        },
            icon = {
                Icon(painterResource(id = R.drawable.health),"health", tint = MaterialTheme.colorScheme.primary)
            }
        )


        NavigationBarItem(selected = navList[3]==current, onClick = {
            homeViewModel.updateCurrentScreen(navList[3])
            navController.navigate("chatBot")

        },
            icon = {
                Icon(painterResource(id = R.drawable.chat_bot),"chat Bot", tint = MaterialTheme.colorScheme.primary)
            },
        )
    }

}