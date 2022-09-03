package com.veygard.currencywizard.presentation.navigation

import com.ramcosta.composedestinations.spec.Direction
import com.veygard.currencywizard.R
import com.veygard.currencywizard.presentation.screens.destinations.AllCurrenciesScreenDestination
import com.veygard.currencywizard.presentation.screens.destinations.ConvertCurrenciesScreenDestination
import com.veygard.currencywizard.presentation.screens.destinations.ErrorScreenDestination
import com.veygard.currencywizard.presentation.screens.destinations.FavoriteCurrenciesScreenDestination

sealed class BottomBarScreen(
    val title: String,
    val icon: Int,
    val destination: Direction
) {

    object All : BottomBarScreen(
        title = "All",
        icon = R.drawable.ic_baseline_view_list_24,
        destination = AllCurrenciesScreenDestination
    )

    object Favorite : BottomBarScreen(
        title = "Favorite",
        icon = R.drawable.ic_baseline_stars_24,
        destination = FavoriteCurrenciesScreenDestination
    )

    object Convert : BottomBarScreen(
        title = "Convert",
        icon = R.drawable.ic_baseline_currency_exchange_24,
        destination = ConvertCurrenciesScreenDestination
    )
}

fun provideBottomBarScreenList() = listOf(BottomBarScreen.All, BottomBarScreen.Favorite, BottomBarScreen.Convert)