package com.example.happima.presentation.Community

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.compose.AppTheme
import com.example.happima.presentation.RenderScreen
import com.example.happima.presentation.database.RepositoryImp
import com.example.happima.presentation.home.HomeViewModel
import com.example.happima.ui.theme.alegreya
import kotlinx.coroutines.coroutineScope

@Composable
fun CommunityUi(repository: RepositoryImp,communityViewModel: CommunityViewModel,homeViewModel: HomeViewModel, navController: NavController){
    val state = communityViewModel.uiState.collectAsState().value

    LaunchedEffect(Unit) {

        communityViewModel.updateFeed()

    }
    RenderScreen(repository = repository,homeViewModel = homeViewModel, navController = navController) {
        Scaffold(topBar = { UserInput(communityViewModel)}) {it ->
            if(state.feedUpdated)
            {
                LazyColumn(modifier = Modifier.padding(it)) {
                    item {
                        Text(text = "helooooooooooooooooooooooooooooooo")
                    }
                    items(state.feed){
                        FeedPost(content = it.content, userName = it.userData?.username ?: "Ma")
                    }
                }
            }
            else{
                Column(modifier = Modifier.padding(it)) {
                    Text(text = "bbbbbbbbbbbbbbbbbbbbbbbbbbb")
                    CircularProgressIndicator()
                }


            }

        }
    }
}

@Composable
fun UserInput(communityViewModel: CommunityViewModel){
    var input by remember {
        mutableStateOf("")
    }
    Card {
        Row {
            TextField(leadingIcon = { Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "profile pic") },
                trailingIcon = {IconButton(onClick = { //
                    //check if message is empty
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
fun FeedPost(content : String, userName : String){
    Card {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
                Icon(imageVector = Icons.Filled.Face, contentDescription = "user")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = userName , fontFamily = alegreya, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
            Text(
                text = content,
                textAlign = TextAlign.Left,
                fontSize = 20.sp,
                fontFamily = alegreya,
                fontWeight = FontWeight.Normal
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun FeedPostPreview(){
    AppTheme {
        FeedPost(userName = "Nayan",content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin pretium faucibus pretium.")
    }
}
@Preview(showBackground = true)
@Composable
fun UserInputPreview(){
    AppTheme {
    }
}