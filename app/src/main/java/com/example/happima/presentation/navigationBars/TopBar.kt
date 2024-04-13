package com.example.happima.presentation.navigationBars

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.compose.AppTheme
import com.example.happima.LoadedResource.Resource
import com.example.happima.presentation.database.RepositoryImp
import com.example.happima.presentation.sign_in.UserData
import com.example.happima.presentation.home.HomeViewModel

@Composable
fun TopBar(repository: RepositoryImp,viewModel: HomeViewModel, navController: NavController?){
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    val homeUiState by viewModel.homeUiState.collectAsState()
    val moodList = Resource.provideMoodList()
    var userData : UserData? = null
    repository.getUserData {
        userData= it
    }

    Row(horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .fillMaxWidth()
            .padding(top = statusBarHeight,start = 5.dp, end = 5.dp)) {
        IconButton(onClick = {
            viewModel.showMoodDialog(true)
        }) {
            val moodAnimate by animateFloatAsState(targetValue = if ( homeUiState.currentMood!= null) 1f else 0f)
            Image(painter = painterResource(if ( homeUiState.currentMood!= null) moodList[homeUiState.currentMood!!].image else moodList[0].image), contentDescription = null, modifier = Modifier.size(50.dp).alpha(if ( homeUiState.currentMood!= null) 1f else 0f))

        }
        IconButton(onClick = { navController?.navigate("setting") }, modifier = Modifier.size(50.dp)) {
            ProfileImage(userData = userData, size = 40, modifier = Modifier)
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
    AppTheme {
    }
}

