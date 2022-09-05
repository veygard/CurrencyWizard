package com.veygard.currencywizard.presentation.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)

val bottomBarTextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontSize = 12.sp,
    fontWeight = FontWeight.W700,
    lineHeight = 16.sp,
    textAlign = TextAlign.Start,
)

val H_L5 = TextStyle(
    fontFamily = FontFamily.Default,
    fontSize = 20.sp,
    fontWeight = FontWeight.W700,
    lineHeight = 24.sp,
    textAlign = TextAlign.Center,
)

val H_L4 = TextStyle(
    fontFamily = FontFamily.Default,
    fontSize = 24.sp,
    fontWeight = FontWeight.W700,
    lineHeight = 32.sp,
    textAlign = TextAlign.Center,
)

val buttonTextStyle = TextStyle(
    fontFamily = FontFamily.Default,
    fontSize = 16.sp,
    fontWeight = FontWeight.W500,
    lineHeight = 24.sp,
    textAlign = TextAlign.Center
)


@Composable
fun titleAuthTextStyle() = TextStyle(
    fontSize = 16.sp,
    fontWeight = FontWeight.W400,
    lineHeight = 20.sp,
    textAlign = TextAlign.Start,
    color =  MaterialTheme.colors.secondary
)

val Paragraph_16_Medium = TextStyle(
    fontFamily = FontFamily.Default,
    fontSize = 16.sp,
    fontWeight = FontWeight.W500,
    lineHeight = 20.sp,
)

val H_L3 = TextStyle(
    fontFamily = FontFamily.Default,
    fontSize = 28.sp,
    fontWeight = FontWeight.W700,
    lineHeight = 36.sp,
    textAlign = TextAlign.Start,
)

val Paragraph_16_longed = TextStyle(
    fontFamily = FontFamily.Default,
    fontSize = 16.sp,
    fontWeight = FontWeight.W800,
    lineHeight = 24.sp,
    textAlign = TextAlign.Start
)

val Paragraph_16 = TextStyle(
    fontFamily = FontFamily.Default,
    fontSize = 16.sp,
    fontWeight = FontWeight.W400,
    lineHeight = 20.sp,
    textAlign = TextAlign.Start
)

@Composable
fun onBackgroundTextStyle() = TextStyle(
    fontSize = 16.sp,
    fontWeight = FontWeight.W500,
    lineHeight = 20.sp,
    textAlign = TextAlign.Start,
    color = MaterialTheme.colors.onBackground
)
