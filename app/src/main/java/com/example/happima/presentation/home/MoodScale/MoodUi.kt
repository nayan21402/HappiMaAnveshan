package com.example.happima.presentation.home.MoodScale

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.compose.AppTheme
import com.example.happima.LoadedResource.Resource
import com.example.happima.presentation.sign_in.UserData
import com.example.happima.presentation.database.CloudDatabase
import com.example.happima.ui.theme.alegreya
import java.util.Date


@Composable
fun MoodUi(CloudDatabase: CloudDatabase?, userData: UserData?, onDismiss: ()-> Unit){
    Dialog(onDismissRequest = { /*TODO*/ }) {
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),modifier = Modifier.clip(RoundedCornerShape(10))) {
            val moodList = Resource.provideMoodList()

            var selectedMood by rememberSaveable {
                mutableIntStateOf(2)
            }
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(5.dp)) {
                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "${userData?.username} how are you feeling today?", fontSize = 25.sp, fontFamily = alegreya, fontWeight = FontWeight.Bold, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp), textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    moodList.forEachIndexed { index, moodData ->
                        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                            .weight(1f)
                            .padding(10.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { selectedMood = index }) {
                            val moodSize by animateFloatAsState(targetValue = if (selectedMood==index) 1.35f else 1f)
                            val moodAlpha by animateFloatAsState(targetValue = if (selectedMood==index) 1f else 0.75f)

                            val moodIndicator by animateColorAsState(targetValue =if (selectedMood == index) MaterialTheme.colorScheme.primary else Color.Transparent)
                            Image(painter = moodData.image, contentDescription = moodData.mood, modifier = Modifier
                                .wrapContentHeight()
                                .scale(moodSize)
                                .alpha(moodAlpha))

                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp, start = 15.dp, end = 15.dp)
                                .height(10.dp)
                                .clip(RoundedCornerShape(50))

                                .background(moodIndicator)
                            )
                        }
                    }
                }
                Button(onClick = {
                    CloudDatabase?.addMoodDb(moodDataDb(Date(),selectedMood),userData)
                    onDismiss()
                }
                ) {
                    Text(text = "Submit", fontFamily = alegreya, fontWeight = FontWeight.Bold )
                }
                Spacer(modifier = Modifier.height(20.dp))

            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun MoodUiPreview(){
    AppTheme {
        MoodUi(null,null,{})
    }
}

