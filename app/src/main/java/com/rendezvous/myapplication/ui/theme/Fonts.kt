package com.rendezvous.myapplication.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.rendezvous.myapplication.R

class Fonts {
     fun playPenSansFamily() = FontFamily(
        Font(R.font.playpensanslight, FontWeight.Light),
        Font(R.font.playpensansregular, FontWeight.Normal),
        Font(R.font.playpensansthin, FontWeight.Normal, FontStyle.Italic),
        Font(R.font.playpensansmedium, FontWeight.Medium),
        Font(R.font.playpensansbold, FontWeight.Bold)
    )
    fun exo() = FontFamily(
        Font(R.font.exo, FontWeight.Normal)
    )
}