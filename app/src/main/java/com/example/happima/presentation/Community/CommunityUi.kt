package com.example.happima.presentation.Community

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.compose.AppTheme
import com.example.happima.presentation.RenderScreen
import com.example.happima.presentation.database.RepositoryImp
import com.example.happima.presentation.home.HomeViewModel
import com.example.happima.presentation.sign_in.UserData
import com.example.happima.ui.theme.alegreya
import kotlinx.coroutines.coroutineScope

@Composable
fun CommunityUi(repository: RepositoryImp,communityViewModel: CommunityViewModel,homeViewModel: HomeViewModel, navController: NavController){
    val state = communityViewModel.uiState.collectAsState().value
    communityViewModel.updateFeed()
    var userData: UserData? = null
    repository.getUserData { userData=it }
    RenderScreen(repository = repository,homeViewModel = homeViewModel, navController = navController) {

        Column {
            Column {
                UserInput(userData,communityViewModel)
                Row(modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(5)),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "HappiCommunity", color = MaterialTheme.colorScheme.onSurface, fontFamily = alegreya, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    IconButton(onClick = { communityViewModel.updateFeed() }) {
                        Icon(imageVector = Icons.Filled.Refresh, tint = MaterialTheme.colorScheme.onSurface, contentDescription = "refresh")
                    }
                }
            }
            if(state.feedUpdated)
            {
                LazyColumn(modifier = Modifier
                    .padding(10.dp)) {
                    items(state.feed){
                        FeedPost(userMessage=it, content = it.content,userData=it.userData, replyList = it.replyList,communityViewModel)
                    }
                }
            }
            else{
                Column(modifier = Modifier) {
                    CircularProgressIndicator()
                }


            }
        }




        }
}

@Composable
fun UserInput(userData: UserData?,communityViewModel: CommunityViewModel){
    var input by remember {
        mutableStateOf("")
    }
    Card(
        Modifier
            .padding(10.dp)
            .fillMaxWidth()) {
        Row(modifier = Modifier,verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            TextField(modifier = Modifier.fillMaxWidth(),
                leadingIcon = { ProfileImage(userData = userData, size = 40, modifier = Modifier)},
                trailingIcon = {IconButton(onClick = { //
                    //check if message is empty
                    if(input.length>1)
                     communityViewModel.updateInput(input)
                     communityViewModel.addMessage()
                }
                ) {
                    Icon(imageVector = Icons.Filled.Check, contentDescription = "Post")
                }
                               },
                value = input,
                onValueChange = {
                    if(it.length<100)
                        input=it
                }
            )

        }
    }
}

@Composable
fun FeedPost(userMessage: userMessage?,content : String, userData : UserData?, replyList : List<userMessage>,communityViewModel: CommunityViewModel){

    var liked by remember {
        mutableStateOf(false)
    }
    var reply by remember {
        mutableStateOf(false)
    }
    var viewReply by remember {
        mutableStateOf(false)
    }
    Card(modifier = Modifier.padding(5.dp)) {
        Column(modifier = Modifier.padding(5.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
                if(userData!=null)ProfileImage(userData = userData, size = 30, modifier = Modifier)
                else Icon(imageVector = Icons.Filled.Face, modifier = Modifier.size(30.dp), contentDescription ="no profile pic" )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = userData?.username?:"user" , fontFamily = alegreya, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
            Row(modifier = Modifier
                .padding(start = 10.dp, top = 10.dp, bottom = 5.dp)) {
                Text(
                    text = content,
                    textAlign = TextAlign.Left,
                    fontSize = 20.sp,
                    fontFamily = alegreya,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.weight(0.9f)
                )
                IconButton(onClick = { liked=!liked }) {
                    if(liked==false)
                        Icon(imageVector = Icons.Filled.FavoriteBorder, contentDescription = "Like", modifier = Modifier
                            .weight(0.1f)
                            .scale(1.1f))
                    else
                        Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Like", modifier = Modifier.weight(0.1f))

                }

            }
            var input by remember {
                mutableStateOf("")
            }
            AnimatedVisibility(visible = reply) {
                TextField(modifier = Modifier.fillMaxWidth(), colors = TextFieldDefaults.colors(unfocusedContainerColor = MaterialTheme.colorScheme.surfaceDim,focusedContainerColor = MaterialTheme.colorScheme.surfaceDim),
                    leadingIcon = { ProfileImage(userData = userData, size = 40, modifier = Modifier)},
                    trailingIcon = {IconButton(onClick = { //
                        if(input.length==0)
                            reply=false
                        else{
                            if (userMessage != null) {
                                communityViewModel.updateReplyInput(input)
                                input=""
                                communityViewModel.addReply(userMessage)
                                communityViewModel.updateReplyInput(input)
                            }

                        }

                    }
                    ) {
                        if(input.length==0)
                            Icon(imageVector = Icons.Filled.Close, contentDescription = "Post")
                        else
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "Post")
                    }
                    },
                    value = input,
                    onValueChange = {
                        if(it.length<100)
                            input=it
                    }
                )
            }
            Row {
                Text(text = "Reply", modifier = Modifier
                    .alpha(if (reply) 0f else 1f)
                    .padding(start = 10.dp)
                    .clickable {
                        reply = !reply
                    }, fontFamily = alegreya)
                Text(text = "view replies", modifier = Modifier
                    .alpha(if (reply) 0f else 1f)
                    .padding(start = 10.dp)
                    .clickable {
                        viewReply = !viewReply
                    }, fontFamily = alegreya)
            }

            AnimatedVisibility(visible = viewReply,modifier = Modifier
                .wrapContentHeight()
                .heightIn(0.dp, 400.dp)) {
                if (userMessage != null) {
                    PostReply(replyList = userMessage.replyList)
                }
           }

        }

        }

}

@Composable
fun PostReply(replyList: List<userMessage>){
    LazyColumn() {
        items(replyList){
                it ->
            Column(modifier = Modifier.padding(5.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    ProfileImage(
                        userData = it.userData,
                        size = 30,
                        modifier = Modifier
                    )

                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = it.userData?.username ?: "user",
                        fontFamily = alegreya,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp, bottom = 5.dp)
                ) {
                    Text(
                        text = it.content,
                        textAlign = TextAlign.Left,
                        fontSize = 20.sp,
                        fontFamily = alegreya,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.weight(0.9f)
                    )
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
@Preview(showBackground = true)
@Composable
fun FeedPostPreview(){


}
@Preview(showBackground = true)
@Composable
fun UserInputPreview(){
    AppTheme {
    }
}