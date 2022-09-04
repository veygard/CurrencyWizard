package com.veygard.currencywizard.presentation.screens.all

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.veygard.currencywizard.domain.model.Currency
import com.veygard.currencywizard.presentation.navigation.BottomBarScreen
import com.veygard.currencywizard.presentation.navigation.provideBottomBarScreenList
import com.veygard.currencywizard.presentation.screens.destinations.ErrorScreenDestination
import com.veygard.currencywizard.presentation.screens.destinations.StartScreenDestination
import com.veygard.currencywizard.presentation.ui.Margin
import com.veygard.currencywizard.presentation.ui.SpacingVertical
import com.veygard.currencywizard.presentation.ui.components.BottomBar
import com.veygard.currencywizard.presentation.ui.components.CurrencyListCompose
import com.veygard.currencywizard.presentation.ui.components.autocomplite.SearchBarWithAutoComplete

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

    val onFavoriteClick: (String, Boolean) -> Unit = { currency, isFavorite ->
        viewModel.changeFavoriteState(currency, isFavorite)
    }

    val onCurrencyClick: (String) -> Unit = { currency ->
        viewModel.fetchAll(currency)
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
                viewModel.totalList.value,
                onFavoriteClick,
                onCurrencyClick
            )
        }
    }

}

@Composable
private fun AllCurrenciesScreenContent(
    navigator: DestinationsNavigator,
    currencies: List<Currency>,
    totalList: List<Currency>?,
    onFavoriteClick: (String, Boolean) -> Unit,
    onCurrencyClick: (String) -> Unit,
) {
    Scaffold(
        bottomBar = { BottomBar(navigator, provideBottomBarScreenList(), BottomBarScreen.All) }
    ) {
        SwipeRefresh(state = rememberSwipeRefreshState(
            false
        ), onRefresh = {

        }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Margin.horizontalStandard, end = Margin.horizontalStandard)
            ) {
                TopBarContent(totalList, onCurrencyClick)
                CurrencyListCompose(currencies = currencies, onFavoriteClick = onFavoriteClick)
            }
        }
    }
}

@Composable
private fun TopBarContent(
    totalList: List<Currency>?,
    onCurrencyClick: (String) -> Unit,
) {
    val showSearchBarState = remember { mutableStateOf(false) }

    Column {
        AnimatedVisibility(
            visible = showSearchBarState.value,
            enter = fadeIn(animationSpec = tween(500)) +
                    expandVertically(
                        animationSpec = tween(
                            500,
                        )
                    ),
            exit = fadeOut(animationSpec = tween(500)) +
                    shrinkVertically(
                        animationSpec = tween(
                            500,
                        )
                    )
        ) {
            SearchBarWithAutoComplete(
                modifier = Modifier
                    .padding(
                        start = Margin.horizontalStandard,
                        end = Margin.horizontalStandard
                    ),
                totalList = totalList ?: emptyList(),
                onCurrencyClick = onCurrencyClick,
                showSearchBarState = showSearchBarState
            )

        }
        if (!showSearchBarState.value) {

        }
    }
}


