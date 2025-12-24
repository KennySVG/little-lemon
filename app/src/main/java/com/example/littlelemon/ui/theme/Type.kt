package com.example.littlelemon.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.littlelemon.R
val Karla = FontFamily(
    Font(R.font.karla_regular, FontWeight.Normal)
)

val Markazi = FontFamily(
    Font(R.font.markazitext_regular, FontWeight.Normal)
)

val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = Markazi,
        fontSize = 28.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Karla,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Karla,
        fontSize = 14.sp
    )
)