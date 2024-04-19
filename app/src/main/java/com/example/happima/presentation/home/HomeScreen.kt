package com.example.happima.presentation.home

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.happima.LoadedResource.Resource
import com.example.happima.R
import com.example.happima.presentation.RenderScreen
import com.example.happima.presentation.database.CloudDatabase
import com.example.happima.presentation.database.RepositoryImp
import com.example.happima.presentation.home.Analytics.Analytics
import com.example.happima.presentation.home.Analytics.AnalyticsViewModel
import com.example.happima.presentation.home.Mindfulness.MindUi
import com.example.happima.presentation.home.MoodScale.MoodUi
import com.example.happima.presentation.home.MoodScale.MoodViewModel
import com.example.happima.presentation.home.Tip.Tip
import com.example.happima.presentation.home.food.MealCard
import com.example.happima.presentation.home.food.RestaurantRec
import com.example.happima.ui.theme.fredoka

@Composable
fun HomeScreen(repository: RepositoryImp,navController: NavController, homeViewModel: HomeViewModel){
    val moodViewModel = MoodViewModel(repository)
    val analyticsViewModel= AnalyticsViewModel(repository)
    val homeUiState = homeViewModel.homeUiState.collectAsState()
    var currentMindfullness by rememberSaveable {
        mutableStateOf("Meditation")
    }
    var index by rememberSaveable {
        mutableStateOf(0)
    }
    val size = Resource.provideMindfullnessList().size

    RenderScreen(repository = repository,homeViewModel = homeViewModel, navController = navController){
        if(homeUiState.value.showMoodDialog){
            MoodUi(moodViewModel) {
                homeViewModel.updateMoodFb()
                homeViewModel.showMoodDialog(false)
            }
        }
        LazyColumn(modifier = Modifier.padding(10.dp)) {
            item {
                Text(text = "Mindfullness", fontFamily = fredoka, fontWeight = FontWeight.Light, fontSize = 25.sp, modifier = Modifier.padding(top = 5.dp))
                Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    IconButton(modifier = Modifier.weight(0.1f),onClick = {
                        // Decrement index, looping to the end if it becomes less than 0
                        index = if (index == 0) size - 1 else index - 1
                    }) {
                        Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = "left")
                    }
                    val context = LocalContext.current
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Resource.getUrlList()[currentMindfullness]))
                   Box(modifier = Modifier
                       .weight(1f)
                       .clickable() {
                           context.startActivity(intent)
                       }){
                       Resource.provideMindfullnessList().forEachIndexed(){imgIndex,item->
                           if(index==imgIndex)
                               currentMindfullness=item.title
                           MindUi(
                               image = item.image,
                               title = item.title,
                               content = item.content,
                               showTime = false,
                               time = item.time,
                               modifier = Modifier.alpha(animateFloatAsState(targetValue = if (index == imgIndex) 1f else 0f).value)

                           )

                       }
                   }

                    IconButton(modifier = Modifier.weight(0.1f),onClick = {
                        // Increment index, looping back to the beginning if it exceeds the list size
                        index = if (index == size-1) 0 else index + 1
                    }) {
                        Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = "left")
                    }
                }
            }

            item {
                Text(text = "Appetite", fontFamily = fredoka, fontWeight = FontWeight.Light, fontSize = 25.sp, modifier = Modifier.padding(top = 5.dp))

                LazyRow {
                    items(Resource.provideMealList()) { it ->
                        MealCard(image = it.image, title = it.title)
                    }
                }
                val context = LocalContext.current
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Resource.getUrlList()["Food"]))
                Box(modifier = Modifier.clickable {
                    context.startActivity(intent)
                }){
                    RestaurantRec(
                        title = "Don't know what to eat? Order from here!",

                    )
                }



            }
            item {


                Row {
                    Column(modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()) {
                        Text(
                            text = "Feeling overwhelmed?",
                            fontFamily = fredoka,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Light,
                            fontSize = 15.sp,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                        Widget(
                            image = R.drawable.happi,
                            title = "Chat with Happi",
                            content = "When life feels heavy, chat with Happi, our virtual counselor who's here for you!",
                            onClick = { navController.navigate("chatBot") },
                            modifier = Modifier
                        )
                    }
                    Column(modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()) {
                        Text(
                            text = "Connect &\n Share",
                            fontFamily = fredoka,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Light,
                            fontSize = 15.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp)
                        )
                        Widget(
                            image = R.drawable.community,
                            title = "Join the Community",
                            content = "Join our mom community for support and shared experiences. You're not alone!",
                            onClick = { navController.navigate("community") },
                        )
                    }
                }

            }



            item {
                Column {
                    Box(modifier = Modifier){
                        Tip(tip = homeUiState.value.tip) {
                            homeViewModel.updateTip()
                        }
                    }
                }

            }
            item{
                Box(modifier = Modifier){
                    Analytics(analyticsViewModel,Modifier)
                }
            }



        }

    }

}

@Preview(showBackground = true)
@Composable
fun LandingScreenPreview(){
}
