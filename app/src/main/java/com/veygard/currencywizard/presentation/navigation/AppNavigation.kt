package com.veygard.currencywizard.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.veygard.currencywizard.presentation.screens.NavGraphs

@Composable
fun AppNavigation(
    navController: NavHostController,
) {
    DestinationsNavHost(
        navGraph = NavGraphs.root,
        navController = navController,
    ) {
        /*Open StartScreen @Destination(start = true) */
    }
}