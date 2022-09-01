package com.veygard.currencywizzard.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.veygard.currencywizzard.presentation.ui.buttonShapes
import com.veygard.currencywizzard.presentation.ui.buttonTextStyle

@Composable
fun CommonButton(
    label: String,
    modifier: Modifier = Modifier,
    enabledStatus: Boolean = true,
    border: BorderStroke? = BorderStroke(width = 1.dp, color = MaterialTheme.colors.onBackground ),
    background: Color = MaterialTheme.colors.primary,
    disabledBackground: Color = MaterialTheme.colors.secondaryVariant,
    leadingContent: @Composable (() -> Unit)? = null,
    backContent: @Composable (() -> Unit)? = null,

    textStyle: TextStyle = buttonTextStyle,
    textColor: Color = MaterialTheme.colors.onPrimary,
    click: () -> Unit,
) {
    Button(
        onClick = click,
        enabled = enabledStatus,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = background,
            disabledBackgroundColor = disabledBackground
        ),
        border = border,
        shape = buttonShapes.medium,
        modifier = modifier
    ) {
        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            leadingContent?.invoke()
            Text(
                text = label,
                modifier = Modifier
                    .height(24.dp),
                style = textStyle,
                color = textColor
            )
            backContent?.invoke()
        }
    }
}