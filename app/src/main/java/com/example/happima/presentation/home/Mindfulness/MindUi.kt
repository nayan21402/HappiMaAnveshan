package com.example.happima.presentation.home.Mindfulness

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.AppTheme
import com.example.happima.R
import com.example.happima.ui.theme.alegreya

@Composable
fun MindUi(image: Int,title : String, content : String, time : Int, showTime : Boolean= true,modifier: Modifier=Modifier){
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(start = 5.dp, end = 5.dp, top = 10.dp, bottom =10.dp)
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(10))
            .clip(RoundedCornerShape(10))
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(start = 5.dp, end = 5.dp, top = 10.dp, bottom =10.dp)
){
        Image(painter = painterResource(id = image), modifier = Modifier
            .padding(10.dp)
            .size(70.dp), contentDescription = "mind image")
        Column(modifier = Modifier.wrapContentHeight()) {
            Text(text = title, fontFamily = alegreya, fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 5.dp))
            Text(text = content, fontFamily = alegreya)
            if(showTime)
                Text(text = "0/$time minutes", modifier = Modifier)

        }

    }
}

@Preview(showBackground = true)
@Composable
fun MindUiPreview(){
    AppTheme {
        MindUi(image = R.drawable.good, title = "yoga", content = "Elevate Your Being: Discover the Harmony Within Through Yoga.", time = 30)

    }

}