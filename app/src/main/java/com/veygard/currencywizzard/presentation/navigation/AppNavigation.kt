package com.veygard.currencywizzard.presentation.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.veygard.currencywizzard.presentation.screens.NavGraphs

@ExperimentalMaterialApi
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