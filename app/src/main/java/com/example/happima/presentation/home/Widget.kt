package com.example.happima.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.AppTheme
import com.example.happima.R
import com.example.happima.ui.theme.alegreya

@Composable
fun Widget(image: Int, title : String, content : String, onClick: () -> Unit,modifier:Modifier=Modifier){
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .clickable {
                onClick()
            }
            .padding(start = 5.dp, end = 5.dp, top = 10.dp, bottom =10.dp)
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(10))
            .clip(RoundedCornerShape(10))
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(start = 5.dp, end = 5.dp, top = 10.dp, bottom =10.dp)){
        Image(painter = painterResource(id = image), modifier = Modifier
            .padding(10.dp)
            .size(100.dp), contentDescription = "mind image")
            Text(text = title, fontFamily = alegreya,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp, modifier = Modifier.padding(bottom = 5.dp).fillMaxWidth())
            Text(text = content, fontFamily = alegreya, textAlign = TextAlign.Center)



    }
}

@Preview(showBackground = true)
@Composable
fun MindUiPreview(){
    AppTheme {
        Widget(image = R.drawable.good, title = "Talk it Out!", content = "Need someone to talk to? Share your feelings with our virtual counselor Happi!",{})

    }

}