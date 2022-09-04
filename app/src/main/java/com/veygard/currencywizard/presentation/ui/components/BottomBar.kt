package com.veygard.currencywizard.presentation.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.veygard.currencywizard.presentation.navigation.BottomBarScreen

@Composable
fun BottomBar(
    navigator: DestinationsNavigator,
    screens: List<BottomBarScreen>,
    selected: BottomBarScreen
) {
    BottomNavigation {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                navigator = navigator,
                selected = selected
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    navigator: DestinationsNavigator,
    selected: BottomBarScreen,
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                painterResource(id = screen.icon),
                contentDescription = "Navigation Icon"
            )
        },
        selected = screen == selected,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navigator.navigate(screen.destination)
        }
    )
}