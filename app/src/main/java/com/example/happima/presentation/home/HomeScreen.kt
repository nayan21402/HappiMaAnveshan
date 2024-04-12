package com.example.happima.presentation.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.happima.presentation.RenderScreen
import com.example.happima.presentation.database.CloudDatabase
import com.example.happima.presentation.database.RepositoryImp
import com.example.happima.presentation.home.Analytics.Analytics
import com.example.happima.presentation.home.Analytics.AnalyticsViewModel
import com.example.happima.presentation.home.MoodScale.MoodUi
import com.example.happima.presentation.home.MoodScale.MoodViewModel
import com.example.happima.presentation.home.Tip.Tip

@Composable
fun HomeScreen(repository: RepositoryImp,navController: NavController, homeViewModel: HomeViewModel){
    val moodViewModel = MoodViewModel(repository)
    val analyticsViewModel= AnalyticsViewModel(repository)
    val homeUiState = homeViewModel.homeUiState.collectAsState()

    RenderScreen(repository = repository,homeViewModel = homeViewModel, navController = navController){
        if(homeUiState.value.showMoodDialog){
            MoodUi(moodViewModel) {
                homeViewModel.updateMoodFb()
                homeViewModel.showMoodDialog(false)
            }
        }
        LazyColumn(modifier = Modifier.padding(10.dp)) {
            item {
                Tip(tip = homeUiState.value.tip) {
                    homeViewModel.updateTip()
                }                
            }
            item {
                Row {
                    Analytics(analyticsViewModel,Modifier.weight(1f))
                    Analytics(analyticsViewModel,Modifier.weight(1f))

                }

            }

        }

    }

}

@Preview(showBackground = true)
@Composable
fun LandingScreenPreview(){
}
