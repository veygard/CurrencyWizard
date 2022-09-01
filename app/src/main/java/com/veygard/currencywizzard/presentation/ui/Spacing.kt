package com.veygard.currencywizzard.presentation.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SpacingVertical(heightDp: Double) {
    Spacer(
        modifier = Modifier
            .height(heightDp.dp)
    )
}

@Composable
fun SpacingVertical(heightDp: Int) {
    Spacer(
        modifier = Modifier
            .height(heightDp.dp)
    )
}
@Composable
fun SpacingHorizontal(WidthDp: Double) {
    Spacer(
        modifier = Modifier
            .width(WidthDp.dp)
    )
}

@Composable
fun SpacingHorizontal(WidthDp: Int) {
    Spacer(
        modifier = Modifier
            .width(WidthDp.dp)
    )
}