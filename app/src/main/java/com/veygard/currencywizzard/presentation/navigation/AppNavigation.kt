package com.veygard.currencywizzard.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.veygard.currencywizzard.presentation.screens.NavGraphs

@Composable
fun AppNavigation(
    navController: NavHostController,
) {
    DestinationsNavHost(
        navGraph= NavGraphs.root,
        navController = navController,
    ) {
        /*Открывает экран который указан как стартовый.
        MainScreen в нашем случае
        В поиске: @Destination(start = true)
        */
    }
}