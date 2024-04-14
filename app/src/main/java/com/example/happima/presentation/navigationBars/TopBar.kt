package com.example.happima.presentation.navigationBars

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.compose.AppTheme
import com.example.compose.background
import com.example.happima.LoadedResource.Resource
import com.example.happima.R
import com.example.happima.presentation.database.RepositoryImp
import com.example.happima.presentation.sign_in.UserData
import com.example.happima.presentation.home.HomeViewModel
import com.example.happima.ui.theme.alegreya
import com.example.happima.ui.theme.fredoka

@Composable
fun TopBar(repository: RepositoryImp,viewModel: HomeViewModel, navController: NavController?){
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    val homeUiState by viewModel.homeUiState.collectAsState()
    val moodList = Resource.provideMoodList()
    var userData : UserData? = null
    var mood by rememberSaveable {
        mutableStateOf("")
    }
    var moodImage by rememberSaveable {
        mutableStateOf(0)
    }
    var moodColor by remember {
        mutableStateOf(Color.Transparent)
    }
    repository.getUserData {
        userData= it
    }
    var animColor=animateColorAsState(targetValue = moodColor, label = "null")

    Row(horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
            .clip(RoundedCornerShape(bottomStartPercent = 15, bottomEndPercent = 15))
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = statusBarHeight, start = 5.dp, end = 5.dp, bottom = 15.dp)


    ) {
        Column (){
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp), verticalAlignment = Alignment.CenterVertically){
               /*
               Image(modifier = Modifier.scale(4f),painter = painterResource(id = R.drawable.streak), contentDescription = "Streak")
                                */

                Text(modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,text = "HappiMa", fontSize = 20.sp , fontFamily = fredoka, color = MaterialTheme.colorScheme.onPrimary)

            }
            Row(modifier = Modifier.padding(start = 10.dp, top = 15.dp)) {
                Column(modifier = Modifier
                    .size(50.dp)
                    .border(5.dp, color = animColor.value, CircleShape)
                    .clickable { navController?.navigate("setting") }) {
                    ProfileImage(userData = userData, size = 80, modifier = Modifier.padding())
                }
                //
                Column(modifier = Modifier.padding(start=20.dp)) {
                    Text(text = "Hi,${userData?.username}!", color = MaterialTheme.colorScheme.onPrimary, fontFamily = fredoka, fontSize = 25.sp)
                    Row(modifier = Modifier
                        .clickable {
                            viewModel.showMoodDialog(true)

                    }) {
                        val moodAnimate by animateFloatAsState(targetValue = if ( homeUiState.currentMood!= null) 1f else 0f)

                        if ( homeUiState.currentMood!= null) {
                            moodImage=moodList[homeUiState.currentMood!!].image
                            mood=moodList[homeUiState.currentMood!!].mood
                            moodColor=moodList[homeUiState.currentMood!!].color
                        }
                        else {
                            moodImage=moodList[0].image
                            mood=moodList[0].mood
                        }

                        Image(painter = painterResource(moodImage), contentDescription = null, modifier = Modifier
                            .size(20.dp)
                            .alpha(if (homeUiState.currentMood != null) 1f else 0f))
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = mood, color = MaterialTheme.colorScheme.onPrimary, fontFamily = alegreya)

                    }
                }
            }
        }
    }
}

@Composable
fun ProfileImage(userData: UserData?,
                 size: Int,
                 modifier: Modifier){
    if (userData != null) {
        AsyncImage(
            model = userData.profilePictureUrl,
            contentDescription = "Profile picture",
            modifier = modifier
                .size(Dp(size.toFloat()))
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun TopBarPreview(){
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val moodList = Resource.provideMoodList()

    AppTheme {
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStartPercent = 10, bottomEndPercent = 10))
                .background(Color.Green)
        ) {
            Column {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp), verticalAlignment = Alignment.CenterVertically){
                    Image(modifier = Modifier.scale(4f),painter = painterResource(id = R.drawable.streak), contentDescription = "Streak")
                    Text(modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,text = "HappiMa", fontSize = 30.sp , fontFamily = fredoka)

                }
                Row(modifier = Modifier.padding(top = 5.dp)) {
                    IconButton(onClick = { }, modifier = Modifier
                        .size(50.dp)
                        .border(2.dp, Color.White, CircleShape)) {
                        Icon(imageVector = Icons.Filled.AccountCircle, modifier = Modifier.size(80.dp), contentDescription ="acc" )
                    }
                    Column(modifier = Modifier) {
                        Text(text = "Hi,Nayan!", fontFamily = fredoka, fontSize = 25.sp)

                            Row {
                                Image(painter = painterResource(moodList[0].image), contentDescription = null, modifier = Modifier
                                    .size(20.dp)

                                )
                                Text(text = "Sad")
                            }


                    }
                }
            }
        }
    }

}

