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
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.happima.R
import com.example.happima.presentation.home.HomeViewModel

import kotlinx.coroutines.selects.select


@Composable
fun BottomBar(navController: NavController, homeViewModel: HomeViewModel) {
    val navBarHeight = WindowInsets.statusBars.asPaddingValues().calculateBottomPadding()

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
    val navList = listOf("community", "home", "help", "chatBot") // Route names

    NavigationBar(modifier = Modifier.padding(bottom = navBarHeight)) {
        navList.forEach { route ->
            NavigationBarItem(
                selected = route == currentDestination,
                onClick = {
                    navController.navigate(route) {
                        // Pop up to the start destination of the graph to
                        // avoid creating a new instance of the destination
                        // if it is already in the back stack
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    when (route) {
                        "community" -> Icon(
                            painterResource(id = R.drawable.group),
                            "Communities",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        "home" -> Icon(
                            Icons.Filled.Home,
                            "Home",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        "help" -> Icon(
                            painterResource(id = R.drawable.health),
                            "Health",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        "chatBot" -> Icon(
                            painterResource(id = R.drawable.chat_bot),
                            "Chat Bot",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        // Add more cases for other routes if needed
                        else -> Unit
                    }
                }
            )
        }
    }
}
