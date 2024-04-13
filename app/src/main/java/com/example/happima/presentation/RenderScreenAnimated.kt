package com.example.happima.presentation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.happima.R

@Composable
fun RenderScreenAnimated(content :@Composable () -> Unit){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
        ,
        contentAlignment = Alignment.Center
    ) {
        val inf = rememberInfiniteTransition(label = "bg")
        val bg by inf.animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec = infiniteRepeatable(
                animation = tween(20000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ), label = "bg"
        )

        Image(painter = painterResource(id = R.drawable.sakura),
            contentDescription = "pattern",
            contentScale = ContentScale.FillHeight,
            alpha = 0.9f,
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentWidth(unbounded = true)
                .offset {
                    IntOffset(x = bg.toInt(), y = 0 )
                }
        )

        content()
    }
}