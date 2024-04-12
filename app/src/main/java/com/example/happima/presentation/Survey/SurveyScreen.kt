package com.example.happima.presentation.Survey

import androidx.annotation.StringRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.compose.AppTheme
import com.example.happima.R
import com.example.happima.presentation.RenderScreenAnimated

import com.example.happima.ui.theme.alegreya

@Composable
fun SurveyUi(modifier: Modifier,@StringRes ques:Int, @StringRes opt1: Int, @StringRes opt2:Int, @StringRes opt3: Int, @StringRes opt4: Int, onClick: () -> Unit){
    val radioOptions = listOf(stringResource(id = opt1), stringResource(id = opt2),
        stringResource(id = opt3), stringResource(id = opt4))
    var selectedOption by remember {
        mutableStateOf("na")
    }



    Column(modifier=modifier,verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stringResource(id = ques), fontFamily = alegreya, fontSize = 35.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, lineHeight = 40.sp)
        Spacer(modifier = Modifier.height(20.dp))
        var anySelected by remember {
            mutableStateOf(false)
        }
        radioOptions.forEach { text ->
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
                , modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(if(text==selectedOption) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background)
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            selectedOption = text
                            anySelected = true
                        }
                    )
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = text,
                    modifier = Modifier.padding(start = 16.dp),
                    color=if(text==selectedOption) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
                    fontFamily = alegreya,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp
                )
                RadioButton(
                    selected = (text == selectedOption),
                    colors = RadioButtonColors(selectedColor = MaterialTheme.colorScheme.onPrimary, unselectedColor = MaterialTheme.colorScheme.primary, disabledUnselectedColor = MaterialTheme.colorScheme.outline, disabledSelectedColor = MaterialTheme.colorScheme.outlineVariant),
                    onClick = {
                        selectedOption=text
                        anySelected=true
                    },
                )


            }
        }

        Button(onClick = {
            onClick()
            anySelected=false
                         } , modifier = Modifier
            .fillMaxWidth()
            .padding(start = 50.dp, end = 50.dp),
            enabled = anySelected,
            colors = ButtonDefaults.buttonColors( disabledContainerColor = Color.LightGray)
        ) {

            Text(text = "Continue", fontFamily = alegreya, fontSize = 20.sp)

        }

    }
}

@Composable
fun SurveyScreen(navController: NavController?) {
    var i by remember { mutableStateOf(0) }
    val ques = listOf(
        R.string.ques1,
        R.string.ques2,
        R.string.ques3,
        R.string.ques4,
        R.string.ques5,
        R.string.ques6,
        R.string.ques7,
        R.string.ques8,
        R.string.ques9,
        R.string.ques10
    )
    val quesMap = mapOf(
        ques[0] to listOf(R.string.q1opt0, R.string.q1opt1, R.string.q1opt2, R.string.q1opt3),
        ques[1] to listOf(R.string.q2opt0, R.string.q2opt1, R.string.q2opt2, R.string.q2opt3),
        ques[2] to listOf(R.string.q3opt0, R.string.q3opt1, R.string.q3opt2, R.string.q3opt3),
        ques[3] to listOf(R.string.q4opt0, R.string.q4opt1, R.string.q4opt2, R.string.q4opt3),
        ques[4] to listOf(R.string.q5opt0, R.string.q5opt1, R.string.q5opt2, R.string.q5opt3),
        ques[5] to listOf(R.string.q6opt0, R.string.q6opt1, R.string.q6opt2, R.string.q6opt3),
        ques[6] to listOf(R.string.q7opt0, R.string.q7opt1, R.string.q7opt2, R.string.q7opt3),
        ques[7] to listOf(R.string.q8opt0, R.string.q8opt1, R.string.q8opt2, R.string.q8opt3),
        ques[8] to listOf(R.string.q9opt0, R.string.q9opt1, R.string.q9opt2, R.string.q9opt3),
        ques[9] to listOf(R.string.q10opt0, R.string.q10opt1, R.string.q10opt2, R.string.q10opt3)
    )
    val prog by animateFloatAsState(targetValue = i/10f)

    RenderScreenAnimated {

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    if(i>0)
                        i--
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Last Question")
                }
                LinearProgressIndicator(strokeCap = StrokeCap.Round,modifier = Modifier
                    .padding(end = 40.dp)
                    .fillMaxWidth(),
                    progress = { prog },
                )
            }


            SurveyUi(modifier=Modifier.weight(0.6f),ques = ques[i], opt1 = quesMap[ques[i]]?.get(0) ?: 0, opt2 = quesMap[ques[i]]?.get(1) ?: 0, opt3 = quesMap[ques[i]]?.get(2) ?: 0, opt4 = quesMap[ques[i]]?.get(3) ?: 0) {
                if(i<9)
                    i++
                else{
                    navController?.navigate("home"){
                        popUpTo("survey"){
                            inclusive=true
                        }
                    }

                }

            }
        }
    }

}






@Preview(showBackground = true)
@Composable
fun PreviewSurveyScreen(){
    AppTheme {
        SurveyScreen(navController = null)
    }
}