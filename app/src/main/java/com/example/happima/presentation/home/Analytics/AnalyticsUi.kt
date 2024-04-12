package com.example.happima.presentation.home.Analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
import com.example.happima.presentation.database.CloudDatabase
import com.example.happima.presentation.home.MoodScale.moodDataDb
import com.example.happima.presentation.sign_in.UserData
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

    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),modifier = modifier
        .clip(
            RoundedCornerShape(10)
        )
        .padding(5.dp)) {
        if (showGraph) {
            val modelProducer = remember { CartesianChartModelProducer.build() }
            LaunchedEffect(Unit) { modelProducer.tryRunTransaction { lineSeries { series(moodPoints.mapNotNull { it.mood }) } } }
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
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            )
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


