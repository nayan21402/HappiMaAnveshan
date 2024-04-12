package com.example.happima.presentation.sign_in

import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.happima.R
import com.example.happima.presentation.RenderScreenAnimated
import com.example.happima.ui.theme.alegreya
import kotlinx.coroutines.delay

@Composable
fun SignInScreen(
    state: SignInState,
    onSignInClick: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    val limit = 0.5f

    val transition = rememberInfiniteTransition(label = "shimmer")
    val progressAnimated by transition.animateFloat(
        initialValue = 0.0f,
        targetValue = limit,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "shimmer"
    )

    RenderScreenAnimated {
        var opacityTarget by remember {
            mutableStateOf(0f)
        }
        var opacityTarget2 by remember {
            mutableStateOf(0f)
        }
        var opacityTarget3 by remember {
            mutableStateOf(0f)
        }
        val opacity by animateFloatAsState(
            targetValue = opacityTarget,
            animationSpec = tween(durationMillis = 1500,
                easing = LinearEasing), label = ""
        )
        val opacity2 by animateFloatAsState(
            targetValue = opacityTarget2,
            animationSpec = tween(durationMillis = 1500,
                easing = LinearEasing), label = ""
        )
        val opacity3 by animateFloatAsState(
            targetValue = opacityTarget3,
            animationSpec = tween(durationMillis = 1500,
                easing = LinearEasing), label = ""
        )
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Image(painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                alpha = opacity,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .size(250.dp)
                    .scale(2.5f)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "HappiMa", fontSize = 60.sp,
                fontFamily = alegreya,
                modifier = Modifier.alpha(opacity),
                fontWeight = FontWeight.ExtraBold)

            Text(text = "Empowering Every Ma\n to Be a Happy Ma", textAlign = TextAlign.Center, fontSize = 25.sp,
                fontFamily = alegreya,
                modifier = Modifier.alpha(opacity2),
                fontWeight = FontWeight.Medium)
            Button(onClick = onSignInClick,
                modifier = Modifier
                    .alpha(opacity3)
                    .padding(top = 25.dp, start = 50.dp, end = 50.dp)
                    .fillMaxWidth()) {
                Text(text = "Sign Up", fontFamily = alegreya, fontWeight = FontWeight.Medium, fontSize = 20.sp)
            }
            LaunchedEffect(key1 = Unit) {
                opacityTarget=1f
                delay(500)
                opacityTarget2=1f
                delay(1000)
                opacityTarget3=1f
            }
        }

    }


}

@Preview(showBackground = true)
@Composable
fun SignInPreview(){
   SignInScreen(state = SignInState()) {
       
   }

}