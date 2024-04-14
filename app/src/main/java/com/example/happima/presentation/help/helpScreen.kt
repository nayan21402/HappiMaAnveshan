package com.example.happima.presentation.help

import android.content.ClipData.Item
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.happima.LoadedResource.Mindfullness
import com.example.happima.LoadedResource.Resource
import com.example.happima.R
import com.example.happima.presentation.RenderScreen
import com.example.happima.presentation.database.RepositoryImp
import com.example.happima.presentation.home.Analytics.Analytics
import com.example.happima.presentation.home.HomeViewModel
import com.example.happima.presentation.home.Mindfulness.MindUi
import com.example.happima.presentation.home.MoodScale.MoodUi
import com.example.happima.presentation.home.Tip.Tip
import com.example.happima.presentation.home.Widget
import com.example.happima.presentation.home.food.MealCard
import com.example.happima.presentation.home.food.RestaurantRec
import com.example.happima.ui.theme.fredoka

@Composable
fun HelpScreen(repository: RepositoryImp, homeViewModel:HomeViewModel, navController:NavController){
    RenderScreen(repository = repository,homeViewModel = homeViewModel, navController = navController){

            LazyColumn (modifier = Modifier.padding(10.dp)){
                item {
                    Text(text = "Get house help", fontFamily = fredoka, fontWeight = FontWeight.Light, fontSize = 25.sp, modifier = Modifier.padding(top = 5.dp))

                    MindUi(
                        image = R.drawable.daima,
                        title = "Daima Support",
                        content = "Get childcare services with our experienced Daima. From personalized care to enriching activities, ensure your child's well-being and development.",
                        showTime = false,
                        time = 0,
                        modifier = Modifier
                    )
                }
                item {
                    Text(text = "Get Checkup!", fontFamily = fredoka, fontWeight = FontWeight.Light, fontSize = 25.sp, modifier = Modifier.padding(top = 5.dp))

                    MindUi(
                        image = R.drawable.doctor,
                        title = "Expert Consultation",
                        content = "Book a consultation with our experienced doctors for personalized medical advice tailored to your needs.",
                        showTime = false,
                        time = 0,
                        modifier = Modifier
                    )
                }

                item {
                    Text(text = "Get Family Planning Guidance!", fontFamily = fredoka, fontWeight = FontWeight.Light, fontSize = 25.sp, modifier = Modifier.padding(top = 5.dp))

                    MindUi(
                        image = R.drawable.familyplan,
                        title = "Family Health Plan",
                        content = "Explore our family health plans designed to cover the healthcare needs of your entire family.",
                        showTime = false,
                        time = 0,
                        modifier = Modifier
                    )
                }



            }
    }

}