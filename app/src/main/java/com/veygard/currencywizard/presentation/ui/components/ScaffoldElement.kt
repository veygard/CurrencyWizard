package com.veygard.currencywizard.presentation.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ScaffoldElement(
    modifier: Modifier = Modifier,
    backgroundColor: Color =  MaterialTheme.colors.background,
    topBarShow: Boolean = false,
    bottomBarShow: Boolean = false,
    topBarContent: @Composable () -> Unit = {},
    bottomBarContent: @Composable () -> Unit = {},
    mainContent: @Composable () -> Unit,
    ) {
    Scaffold(
        modifier = modifier,
        backgroundColor = backgroundColor,
        topBar = {
            if (topBarShow) {
                topBarContent()
            }
        },
        bottomBar = {
            if (bottomBarShow) {
                bottomBarContent()
            }
        }
    ) {
        mainContent()
    }
}