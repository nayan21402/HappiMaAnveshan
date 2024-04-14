package com.example.happima.presentation.home.Tip

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.AppTheme
import com.example.happima.LoadedResource.Resource
import com.example.happima.ui.theme.alegreya

@Composable
fun Tip(@StringRes tip: Int, onClick: () -> Unit){
    val tipList=Resource.provideTipList()
    var expanded by remember {
        mutableStateOf(true)
    }
    Column(modifier = Modifier.shadow(elevation = 10.dp, shape = RoundedCornerShape(10))
        .clip(RoundedCornerShape(10))
        .background(MaterialTheme.colorScheme.onPrimary)
        .padding(5.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Happi Tips",fontFamily = alegreya, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                /*
                IconButton(onClick = { expanded=!expanded}) {
                    Icon(imageVector =if(!expanded) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp, contentDescription = "collapse tips")
                }

                 */
            }
            AnimatedVisibility(visible = expanded) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = stringResource(id = tipList[tip]), fontFamily = alegreya, fontWeight = FontWeight.Medium, fontSize = 15.sp, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(2.dp))
                    IconButton(onClick = { onClick()}) {
                        Icon(imageVector = Icons.Filled.Refresh, contentDescription = "change tip")
                    }
                }

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun TipPreview(){
    AppTheme {
        Tip(0) {}
    }
}


