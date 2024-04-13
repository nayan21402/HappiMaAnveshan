package com.example.happima.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.happima.R

// Set of Material typography styles to start with
/*
val alegreya = FontFamily(
    Font(R.font.alegreya_regular, FontWeight.Normal),
    Font(R.font.alegreya_medium, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.alegreya_medium, FontWeight.Medium),
    Font(R.font.alegreya_bold, FontWeight.Bold),
    Font(R.font.alegreya_black, FontWeight.Black),
    Font(R.font.alegreya_black_italic, FontWeight.Black,FontStyle.Italic),
    Font(R.font.alegreya_extrabold_italic, FontWeight.ExtraBold,FontStyle.Italic),
    Font(R.font.alegreya_extrabold, FontWeight.ExtraBold),
    )


 */

val alegreya = FontFamily(
    Font(R.font.circular_book, FontWeight.Normal),
    Font(R.font.circular_medium, FontWeight.Medium),
    Font(R.font.circular_bold, FontWeight.Bold),
    Font(R.font.circular_black, FontWeight.Black),
    Font(R.font.circular_light,FontWeight.Light)
)
val fredoka = FontFamily(
    Font(R.font.fredoka_regular)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = alegreya,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )

)