package com.veygard.currencywizard.presentation.screens.all

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.veygard.currencywizard.domain.model.CurrencyStuffed
import com.veygard.currencywizard.presentation.navigation.BottomBarScreen
import com.veygard.currencywizard.presentation.navigation.provideBottomBarScreenList
import com.veygard.currencywizard.presentation.screens.destinations.ErrorScreenDestination
import com.veygard.currencywizard.presentation.screens.destinations.StartScreenDestination
import com.veygard.currencywizard.presentation.ui.components.BottomBar
import com.veygard.currencywizard.presentation.ui.components.CurrencyListCompose

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
    
    val onFavoriteClick: (String, Boolean) -> Unit = { currency, isFavorite  ->
        viewModel.changeFavoriteState(currency, isFavorite)
    }

    when (screenState.value) {
        null -> {}
        AllCurrenciesState.ConnectionError -> navigator.navigate(ErrorScreenDestination)
        AllCurrenciesState.ListError -> navigator.navigate(ErrorScreenDestination)
        AllCurrenciesState.NoLocalDb -> navigator.navigate(StartScreenDestination)
        AllCurrenciesState.Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { CircularProgressIndicator() }

        is AllCurrenciesState.CurrencyListReady -> {
            AllCurrenciesScreenContent(
                navigator,
                (screenState.value as AllCurrenciesState.CurrencyListReady).list,
                onFavoriteClick
            )
        }
    }

}

@Composable
fun AllCurrenciesScreenContent(
    navigator: DestinationsNavigator,
    currencies: List<CurrencyStuffed>,
    onFavoriteClick: (String, Boolean) -> Unit
) {
    Scaffold(
        bottomBar = { BottomBar(navigator, provideBottomBarScreenList(), BottomBarScreen.All) }
    ) {
        SwipeRefresh(state = rememberSwipeRefreshState(
            false
        ), onRefresh = {

        }) {
            CurrencyListCompose(currencies = currencies, onFavoriteClick = onFavoriteClick)
        }
    }
}
