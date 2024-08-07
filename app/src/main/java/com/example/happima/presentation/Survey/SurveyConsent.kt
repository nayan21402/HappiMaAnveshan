package com.example.happima.presentation.Survey

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.compose.AppTheme
import com.example.happima.R
import com.example.happima.presentation.RenderScreenAnimated
import com.example.happima.ui.theme.alegreya

@Composable
fun SurveyConsent(navController: NavController?){
    RenderScreenAnimated {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()) {
            var opacityTarget by remember {
                mutableStateOf(1f)
            }
            val opacity by animateFloatAsState(
                targetValue = opacityTarget,
                animationSpec = tween(durationMillis = 1500,
                    easing = LinearEasing
                ), label = ""
            )

            Text(text = stringResource(id = R.string.survey_start),
                fontFamily = alegreya,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.alpha(opacity).padding(10.dp),
                fontWeight = FontWeight.Medium,
                lineHeight = 35.sp,
                fontSize = 30.sp,
                textAlign = TextAlign.Center)
            Image(painter = painterResource(id = R.drawable.happi), modifier = Modifier.size(150.dp), contentDescription = "happi")

            Button(modifier = Modifier.padding(20.dp),onClick = {
                navController?.navigate("survey")
            }) {

                Text(text = "Continue", fontFamily = alegreya, textAlign = TextAlign.Center, fontSize = 20.sp)

                LaunchedEffect(key1 = Unit) {
                    opacityTarget = 1f
                }
            }
        }
    }
}

@Preview
@Composable
fun SurveyConsentPreview(){
    AppTheme {
        SurveyConsent(navController = null)
    }
}