package com.veygard.currencywizard.presentation.screens.all

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.veygard.currencywizard.R
import com.veygard.currencywizard.domain.model.Currency
import com.veygard.currencywizard.presentation.navigation.BottomBarScreen
import com.veygard.currencywizard.presentation.navigation.provideBottomBarScreenList
import com.veygard.currencywizard.presentation.screens.destinations.ErrorScreenDestination
import com.veygard.currencywizard.presentation.screens.destinations.StartScreenDestination
import com.veygard.currencywizard.presentation.ui.Margin
import com.veygard.currencywizard.presentation.ui.SpacingHorizontal
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
    val pickedCurrency = viewModel.pickedCurrency.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.fetchAll()
    })

    val onFavoriteClick: (String, Boolean) -> Unit = { currency, isFavorite ->
        viewModel.changeFavoriteState(currency, isFavorite)
    }

    val onCurrencyClick: (String) -> Unit = { currency ->
        viewModel.updatePickedCurrency(currency)
        viewModel.fetchAll()
    }


    when (screenState.value) {
        null -> {}
        AllCurrenciesState.ConnectionError -> navigator.navigate(ErrorScreenDestination)
        AllCurrenciesState.ListError -> navigator.navigate(ErrorScreenDestination)
        AllCurrenciesState.NoLocalDb -> navigator.navigate(StartScreenDestination)
        is AllCurrenciesState.CurrencyListReady, AllCurrenciesState.Loading -> {
            AllCurrenciesScreenContent(
                navigator,
                viewModel.totalList.value,
                onFavoriteClick,
                onCurrencyClick,
                pickedCurrency,
                screenState
            )
        }
    }

}

@Composable
private fun AllCurrenciesScreenContent(
    navigator: DestinationsNavigator,
    totalList: List<Currency>?,
    onFavoriteClick: (String, Boolean) -> Unit,
    onCurrencyClick: (String) -> Unit,
    pickedCurrency: State<String?>,
    screenState: State<AllCurrenciesState?>,
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
                TopBarContent(totalList, onCurrencyClick, pickedCurrency)
                when (screenState.value) {
                    AllCurrenciesState.Loading -> Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator() }
                    is AllCurrenciesState.CurrencyListReady -> CurrencyListCompose(
                        currencies = (screenState.value as AllCurrenciesState.CurrencyListReady).list,
                        onFavoriteClick = onFavoriteClick
                    )
                    else -> {}
                }
            }
        }
    }
}

@Composable
private fun TopBarContent(
    totalList: List<Currency>?,
    onCurrencyClick: (String) -> Unit,
    pickedCurrency: State<String?>,
) {
    val showSearchBarState = remember { mutableStateOf(false) }
    Log.d("testing_something", "picked: ${pickedCurrency.value}")
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    modifier = Modifier.weight(3f),
                    onClick = {
                        showSearchBarState.value = true
                    }, elevation = ButtonDefaults.elevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 9.dp,
                        disabledElevation = 2.dp
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colors.primary),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.primary)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = pickedCurrency.value ?: "USD",
                        textAlign = TextAlign.Center
                    )
                }
                SpacingHorizontal(WidthDp = 16)
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_sort_24),
                        contentDescription = "sorting",
                        tint = MaterialTheme.colors.primary
                    )
                }
            }

        }
    }
}


