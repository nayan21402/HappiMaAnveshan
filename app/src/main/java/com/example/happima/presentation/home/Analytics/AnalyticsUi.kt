package com.example.happima.presentation.home.Analytics

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.AppTheme
import com.example.happima.LoadedResource.Resource
import com.example.happima.presentation.database.CloudDatabase
import com.example.happima.presentation.home.MoodScale.moodDataDb
import com.example.happima.presentation.sign_in.UserData
import com.example.happima.ui.theme.alegreya
import com.example.happima.ui.theme.fredoka
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.compose.component.shape.shader.color
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.model.lineSeries

@Composable
fun Analytics(analyticsViewModel: AnalyticsViewModel, modifier:Modifier) {
    var moodPoints = analyticsViewModel.uiState.collectAsState().value.moodPoints
    var showGraph =analyticsViewModel.uiState.collectAsState().value.showGraph

    analyticsViewModel.fetchMoodPoints()

    Column(modifier= Modifier
        .padding(10.dp)
        .shadow(elevation = 10.dp, shape = RoundedCornerShape(10))
        .clip(RoundedCornerShape(10))
        .background(MaterialTheme.colorScheme.onPrimary)
        .fillMaxHeight()) {
        Text(text = "Mood Graph", fontFamily = alegreya, fontSize = 15.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center,modifier=Modifier.fillMaxWidth())
        if (showGraph) {
            val modelProducer = remember { CartesianChartModelProducer.build() }
            LaunchedEffect(Unit) { modelProducer.tryRunTransaction { lineSeries { series(moodPoints.mapNotNull {
                when(it.mood){
                    0 -> 4
                    1 -> 3
                    2 -> 2
                    3 -> 1
                    4 -> 0
                    else -> 0
                }
            }) } } }
            Row(modifier = Modifier.fillMaxHeight()) {
                Box(modifier = Modifier.fillMaxHeight().weight(0.1f)){
                    Column(modifier = Modifier.padding(start = 2.5.dp )
                        , verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally) {
                        val moodList = Resource.provideMoodList()
                        moodList.forEachIndexed { it, moodData ->
                            var index=0
                            when(it){
                                0 -> index=4
                                1 -> index=3
                                2 -> index=2
                                3 -> index=1
                                4 -> index=0
                                else -> index=1
                            }
                            Image(painter = painterResource(id = moodList[index].image), contentDescription = moodData.mood, modifier = Modifier.fillMaxSize().padding(1.dp))
                        }
                    }
                }

                CartesianChartHost(
                    rememberCartesianChart(
                        rememberLineCartesianLayer(listOf(rememberLineSpec(shader = DynamicShaders.color(MaterialTheme.colorScheme.primary), thickness = 3.dp))),
                        startAxis = rememberStartAxis(guideline = null, label = null, title = "Mood"),
                        bottomAxis = rememberBottomAxis(guideline = null, label = null),
                    ),
                    modelProducer,
                    modifier = Modifier
                        .padding(5.dp)
                        .height(150.dp)
                        .background(MaterialTheme.colorScheme.onPrimary)
                        .weight(0.9f)
                )


            }

        } else {
            // Optional: Show a loading indicator while data is fetched
            CircularProgressIndicator(Modifier.size(150.dp))
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AnalyticsPreview() {
    AppTheme {
        Card (){
            Row {
                Column(modifier = Modifier
                    .weight(0.2f)
                    .fillMaxHeight(), verticalArrangement = Arrangement.SpaceAround) {
                    val moodList = Resource.provideMoodList()
                    moodList.forEachIndexed { index, moodData ->
                        Image(painter = painterResource(id = moodData.image), contentDescription = moodData.mood, modifier = Modifier
                            .size(10.dp)
                            .wrapContentHeight())
                    }
                }
                Box(modifier = Modifier.weight(0.8f)){
                    val modelProducer = remember { CartesianChartModelProducer.build() }
                    modelProducer.tryRunTransaction {
                        lineSeries (){
                            // Add a color modifier here
                            series(4, 12, 8, 16)
                        }
                    }
                    CartesianChartHost(
                        chart =
                        rememberCartesianChart(
                            rememberLineCartesianLayer(listOf(rememberLineSpec(shader = DynamicShaders.color(Color.Red), thickness = 3.dp))),
                            startAxis = rememberStartAxis(guideline = null, label = null, title = "Mood"),
                            bottomAxis = rememberBottomAxis(),
                        ),
                        modelProducer = modelProducer, modifier = Modifier.padding(10.dp))
                }

            }

        }


    }
}


