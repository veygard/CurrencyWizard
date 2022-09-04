@file:OptIn(ExperimentalMaterialApi::class)

package com.veygard.currencywizard.presentation.screens.favorite

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.veygard.currencywizard.domain.model.Currency
import com.veygard.currencywizard.presentation.model.SortingOrder
import com.veygard.currencywizard.presentation.model.SortingTypes
import com.veygard.currencywizard.presentation.navigation.BottomBarScreen
import com.veygard.currencywizard.presentation.navigation.provideBottomBarScreenList
import com.veygard.currencywizard.presentation.screens.all.*
import com.veygard.currencywizard.presentation.screens.destinations.ErrorScreenDestination
import com.veygard.currencywizard.presentation.ui.Margin
import com.veygard.currencywizard.presentation.ui.SpacingVertical
import com.veygard.currencywizard.presentation.ui.components.BottomBar
import com.veygard.currencywizard.presentation.ui.components.currencylist.CurrencyListCompose
import com.veygard.currencywizard.presentation.ui.components.currencylist.CurrencyTopBarContent
import com.veygard.currencywizard.presentation.ui.components.currencylist.SortingBottomSheetContent
import kotlinx.coroutines.launch

@Composable
@Destination
fun FavoriteCurrenciesScreen(
    navigator: DestinationsNavigator,
    viewModel: FavoriteCurrenciesViewModel = hiltViewModel(),
) {
    val screenState = viewModel.stateFlow.collectAsState()
    val pickedCurrency = viewModel.pickedCurrency.collectAsState()
    val sortedByState by viewModel.sortedBy.collectAsState()
    val sortedOrderState by viewModel.sortedOrder.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.fetchMulti()
    })

    val onFavoriteClick: (String, Boolean) -> Unit = { currency, isFavorite ->
        viewModel.changeFavoriteState(currency, isFavorite)
    }

    val onCurrencyClick: (String) -> Unit = { currency ->
        viewModel.updatePickedCurrency(currency)
        viewModel.fetchMulti()
    }

    val sortByTypeClick: (SortingTypes) -> Unit = { type ->
        viewModel.sortByType(type)
    }
    val sortByOrderClick: () -> Unit = {
        when (sortedOrderState) {
            SortingOrder.DESCENDING -> viewModel.sortByOder(SortingOrder.ASCENDING)
            SortingOrder.ASCENDING -> viewModel.sortByOder(SortingOrder.DESCENDING)
        }
    }

    when (screenState.value) {
        FavoriteCurrenciesState.ConnectionError -> navigator.navigate(ErrorScreenDestination)
        FavoriteCurrenciesState.ListError -> navigator.navigate(ErrorScreenDestination)
        is FavoriteCurrenciesState.CurrencyListReady, FavoriteCurrenciesState.Loading -> {
            FavoriteCurrenciesScreenContent(
                navigator = navigator,
                totalList = viewModel.totalList.value,
                onFavoriteClick = onFavoriteClick,
                onCurrencyClick = onCurrencyClick,
                pickedCurrency = pickedCurrency,
                screenState = screenState,
                sortedByState = sortedByState,
                sortByTypeClick = sortByTypeClick,
                sortByOrderClick = sortByOrderClick,
                sortedOrderState = sortedOrderState,
                startingDestination = BottomBarScreen.Favorite
            )
        }
        else -> {}
    }
}


@Composable
private fun FavoriteCurrenciesScreenContent(
    navigator: DestinationsNavigator,
    totalList: List<Currency>?,
    onFavoriteClick: (String, Boolean) -> Unit,
    onCurrencyClick: (String) -> Unit,
    pickedCurrency: State<String?>,
    screenState: State<FavoriteCurrenciesState?>,
    sortedByState: SortingTypes,
    sortByTypeClick: (type: SortingTypes) -> Unit,
    sortByOrderClick: () -> Unit,
    sortedOrderState: SortingOrder,
    startingDestination: BottomBarScreen
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutine = rememberCoroutineScope()

    BottomSheetScaffold(
        sheetContent = {
            SortingBottomSheetContent(sortByTypeClick = sortByTypeClick, sortedByState = sortedByState)
            BackHandler(enabled = true) {
                coroutine.launch {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                }
            }
        },
        sheetPeekHeight = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background),
        sheetElevation = 8.dp,
        scaffoldState = bottomSheetScaffoldState,
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        content = {
            Scaffold(
                bottomBar = {
                    BottomBar(
                        navigator,
                        provideBottomBarScreenList(),
                        startingDestination
                    )
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = Margin.horizontalStandard, end = Margin.horizontalStandard)
                ) {
                    CurrencyTopBarContent(
                        totalList=totalList,
                        onCurrencyClick=onCurrencyClick,
                        pickedCurrency=pickedCurrency,
                        bottomSheetScaffoldState=bottomSheetScaffoldState,
                        coroutine=coroutine,
                        sortByOrderClick=sortByOrderClick,
                        onFavoriteClick=onFavoriteClick,
                        sortedOrderState=sortedOrderState
                    )
                    when (screenState.value) {
                        FavoriteCurrenciesState.Loading -> Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) { CircularProgressIndicator() }
                        is FavoriteCurrenciesState.CurrencyListReady -> CurrencyListCompose(
                            currencies = (screenState.value as FavoriteCurrenciesState.CurrencyListReady).list,
                            onFavoriteClick = onFavoriteClick
                        )
                        else -> {}
                    }
                }
            }
        })
}
