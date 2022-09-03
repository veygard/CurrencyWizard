package com.veygard.currencywizard.presentation.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

val buttonShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(13.dp),
    large = RoundedCornerShape(16.dp)
)

val inputFieldsShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(13.dp),
    large = RoundedCornerShape(16.dp)
)

val roundShapes = Shapes(
    small = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    medium = RoundedCornerShape(13.dp),
    large = RoundedCornerShape(24.dp)
)