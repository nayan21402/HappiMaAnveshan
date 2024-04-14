package com.example.happima.presentation.home.food

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
fun FoodUi(image: Int,title : String, content : String, time : Int){
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp, top = 10.dp, bottom = 10.dp)
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(10))
            .clip(RoundedCornerShape(10))
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(start = 5.dp, end = 5.dp, top = 10.dp, bottom = 10.dp)

            .fillMaxWidth()){
        Image(painter = painterResource(id = image), modifier = Modifier
            .padding(10.dp)
            .size(70.dp), contentDescription = "mind image")
        Column(modifier = Modifier.wrapContentHeight()) {
            Text(text = title, fontFamily = alegreya, fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 5.dp))
            Text(text = content, fontFamily = alegreya)
            Text(text = "0/$time minutes", modifier = Modifier)

        }

    }
}

@Composable
fun MealCard(image: Int,title : String, modifier: Modifier = Modifier){
    var radioButton by remember {
        mutableStateOf(false)
    }
    var backgroundColor = if (radioButton )MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary
    var textColor = if (!radioButton )MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary

    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(start = 5.dp, end = 5.dp, top = 10.dp, bottom = 10.dp)
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(10))
            .clip(RoundedCornerShape(10))
            .background(animateColorAsState(targetValue = backgroundColor).value)
            .padding(start = 5.dp, end = 5.dp, top = 10.dp, bottom = 5.dp)
            .width(80.dp)){

        Image(painter = painterResource(id = image), modifier = Modifier
            .padding(10.dp)
            .size(60.dp), contentDescription = "mind image")
        Column(modifier = Modifier.wrapContentHeight(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = title, color = animateColorAsState(targetValue = textColor).value, fontFamily = alegreya, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 15.sp,
                modifier = Modifier
                    .padding()
                    .fillMaxWidth())
            RadioButton(selected = radioButton, colors = RadioButtonColors(selectedColor = MaterialTheme.colorScheme.onPrimary, unselectedColor = MaterialTheme.colorScheme.primaryContainer, disabledSelectedColor = Color.DarkGray, disabledUnselectedColor = Color.DarkGray), onClick = { radioButton=!radioButton })

        }

    }
}

@Composable
fun RestaurantRec(title: String,modifier: Modifier = Modifier){
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp, top = 10.dp, bottom = 10.dp)
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(50))
            .clip(RoundedCornerShape(10))
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(start = 5.dp, end = 5.dp, top = 10.dp, bottom = 10.dp)
            .fillMaxWidth()
    ){
        Icon(imageVector = Icons.Filled.LocationOn, contentDescription = "null")
        Column(modifier = Modifier.wrapContentHeight()) {
            Text(text = title, fontFamily = alegreya, fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.padding(start = 15.dp))


        }

    }
}



@Preview(showBackground = true)
@Composable
fun MindUiPreview(){
        RestaurantRec(title = "Breakfast")



}