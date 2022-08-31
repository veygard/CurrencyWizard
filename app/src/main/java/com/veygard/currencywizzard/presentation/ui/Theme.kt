package com.veygard.currencywizzard.presentation.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
    primary = Accent_Blue, //цвет кнопок
    primaryVariant = lightBlueBackground,
    onPrimary = white_white, //цвет на кнопке
    secondary = Grey_50, //кнопки
    secondaryVariant = Grey_30,
    onSecondary = Grey_21,
    error = Accent_Red,
    onError = Accent_Red,
    background = lightGreyBackground,
    onBackground = Grey_90, //Текст титулов на бекраунде
    surface = white_white, //Бекграунд блоков
    onSurface = Grey_90, //Цвет на них
)

private val DarkColorPalette = darkColors(
    primary = Accent_Blue_900,
    primaryVariant = Grey_60,
    onPrimary = Grey_30,
    secondary = Grey_30, //кнопки
    secondaryVariant = Grey_80,
    onSecondary = white_white,
    error = Accent_Red_900,
    onError = RedErrorLight,
    background = Grey_90,
    onBackground = Grey_30, //Текст титулов на бекраунде
    surface = Grey_70,
    onSurface = Grey_30,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}