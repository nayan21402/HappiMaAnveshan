package com.example.happima.LoadedResource

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.happima.R

data class moodDataUi(val mood: String, val image: Int, val color: Color)

object Resource {
    fun provideMoodList(): List<moodDataUi> {
        return listOf(
            moodDataUi("worst", R.drawable.worst, Color(0xffFF555E)),
            moodDataUi("bad", R.drawable.bad, Color(0xffFF8448)),
            moodDataUi("neutral", R.drawable.neutral, Color(0xffFDCF18)),
            moodDataUi("good", R.drawable.best, Color(0xff9FD772)),
            moodDataUi("best", R.drawable.good, Color(0xff61CA63))
        )
    }

    fun provideTipList(): List<Int> {
        return listOf(
            R.string.tip1, R.string.tip2, R.string.tip3, R.string.tip4, R.string.tip5,
            R.string.tip6, R.string.tip7, R.string.tip8, R.string.tip9, R.string.tip10,
            R.string.tip11, R.string.tip12, R.string.tip13, R.string.tip14, R.string.tip15,
            R.string.tip16, R.string.tip17, R.string.tip18, R.string.tip19, R.string.tip20,
            R.string.tip21, R.string.tip22, R.string.tip23, R.string.tip24, R.string.tip25,
            R.string.tip26, R.string.tip27, R.string.tip28, R.string.tip29, R.string.tip30,
            R.string.tip31, R.string.tip32, R.string.tip33, R.string.tip34, R.string.tip35,
            R.string.tip36, R.string.tip37, R.string.tip38, R.string.tip39, R.string.tip40,
            R.string.tip41, R.string.tip42, R.string.tip43, R.string.tip44, R.string.tip45,
            R.string.tip46, R.string.tip47, R.string.tip48, R.string.tip49, R.string.tip50
        )
    }


}