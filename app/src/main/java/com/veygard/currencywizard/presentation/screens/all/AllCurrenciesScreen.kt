package com.veygard.currencywizard.presentation.screens.all

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.veygard.currencywizard.presentation.navigation.BottomBarScreen
import com.veygard.currencywizard.presentation.navigation.provideBottomBarScreenList
import com.veygard.currencywizard.presentation.screens.destinations.ErrorScreenDestination
import com.veygard.currencywizard.presentation.screens.destinations.StartScreenDestination
import com.veygard.currencywizard.presentation.ui.components.BottomBar

@Composable
@Destination
fun AllCurrenciesScreen(
    navigator: DestinationsNavigator,
    viewModel: AllCurrenciesViewModel = hiltViewModel(),
) {
    val screenState = viewModel.stateFlow.collectAsState()
    LaunchedEffect(key1 = Unit, block = {
        viewModel.fetchAll()
    })

    when (screenState.value) {
        AllCurrenciesState.ConnectionError -> navigator.navigate(ErrorScreenDestination)
        AllCurrenciesState.ListError -> navigator.navigate(ErrorScreenDestination)
        AllCurrenciesState.Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { CircularProgressIndicator() }
        is AllCurrenciesState.CurrencyListReady -> {
            AllCurrenciesScreenContent(navigator)
        }
        AllCurrenciesState.NoLocalDb -> navigator.navigate(StartScreenDestination)
        null -> {}
    }

}

@Composable
fun AllCurrenciesScreenContent(navigator: DestinationsNavigator) {
    Scaffold(
        bottomBar = { BottomBar(navigator, provideBottomBarScreenList(), BottomBarScreen.All) }
    ) {
        Text(text = "All")
    }
}
