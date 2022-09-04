@file:OptIn(ExperimentalMaterialApi::class)

package com.veygard.currencywizard.presentation.screens.all

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.veygard.currencywizard.R
import com.veygard.currencywizard.domain.model.Currency
import com.veygard.currencywizard.presentation.model.SortingOrder
import com.veygard.currencywizard.presentation.model.SortingTypes
import com.veygard.currencywizard.presentation.navigation.BottomBarScreen
import com.veygard.currencywizard.presentation.navigation.provideBottomBarScreenList
import com.veygard.currencywizard.presentation.screens.destinations.ErrorScreenDestination
import com.veygard.currencywizard.presentation.screens.destinations.StartScreenDestination
import com.veygard.currencywizard.presentation.ui.*
import com.veygard.currencywizard.presentation.ui.components.BottomBar
import com.veygard.currencywizard.presentation.ui.components.CurrencyListCompose
import com.veygard.currencywizard.presentation.ui.components.RadioButtonCustom
import com.veygard.currencywizard.presentation.ui.components.autocomplite.SearchBarWithAutoComplete
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
@Destination
fun AllCurrenciesScreen(
    navigator: DestinationsNavigator,
    viewModel: AllCurrenciesViewModel = hiltViewModel(),
) {
    val screenState = viewModel.stateFlow.collectAsState()
    val pickedCurrency = viewModel.pickedCurrency.collectAsState()
    val sortedByState by viewModel.sortedBy.collectAsState()
    val sortedOrderState by viewModel.sortedOrder.collectAsState()

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
        null -> {}
        AllCurrenciesState.ConnectionError -> navigator.navigate(ErrorScreenDestination)
        AllCurrenciesState.ListError -> navigator.navigate(ErrorScreenDestination)
        AllCurrenciesState.NoLocalDb -> navigator.navigate(StartScreenDestination)
        is AllCurrenciesState.CurrencyListReady, AllCurrenciesState.Loading -> {
            AllCurrenciesScreenContent(
                navigator = navigator,
                totalList = viewModel.totalList.value,
                onFavoriteClick = onFavoriteClick,
                onCurrencyClick = onCurrencyClick,
                pickedCurrency = pickedCurrency,
                screenState = screenState,
                sortedByState = sortedByState,
                sortByTypeClick = sortByTypeClick,
                sortByOrderClick = sortByOrderClick,
                sortedOrderState = sortedOrderState
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
    sortedByState: SortingTypes,
    sortByTypeClick: (type: SortingTypes) -> Unit,
    sortByOrderClick: () -> Unit,
    sortedOrderState: SortingOrder,
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutine = rememberCoroutineScope()

    BottomSheetScaffold(
        sheetContent = {
            BottomSheetContent(sortByTypeClick = sortByTypeClick, sortedByState = sortedByState)
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
                        BottomBarScreen.All
                    )
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = Margin.horizontalStandard, end = Margin.horizontalStandard)
                ) {
                    TopBarContent(
                        totalList,
                        onCurrencyClick,
                        pickedCurrency,
                        bottomSheetScaffoldState,
                        coroutine,
                        sortByOrderClick,
                        sortedOrderState
                    )
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
        })
}

@Composable
private fun TopBarContent(
    totalList: List<Currency>?,
    onCurrencyClick: (String) -> Unit,
    pickedCurrency: State<String?>,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    coroutine: CoroutineScope,
    sortByOrderClick: () -> Unit,
    sortedOrderState: SortingOrder,
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
                IconButton(onClick = {
                    coroutine.launch {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    }
                }, modifier = Modifier.weight(1f)) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_sort_24),
                        contentDescription = "sorting order",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }

                IconButton(onClick = {
                    sortByOrderClick()
                }, modifier = Modifier.weight(1f)) {
                    when (sortedOrderState) {
                        SortingOrder.DESCENDING -> Icon(
                            painter = painterResource(id = R.drawable.sort_descending),
                            contentDescription = "sorting",
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        SortingOrder.ASCENDING -> Icon(
                            painter = painterResource(id = R.drawable.sort_ascending),
                            contentDescription = "sorting",
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomSheetContent(
    sortByTypeClick: (type: SortingTypes) -> Unit,
    sortedByState: SortingTypes
) {
    val abcType = stringResource(R.string.radio_button_abc)
    val valueType = stringResource(R.string.radio_button_value)
    val radioOptions = listOf(abcType, valueType)
    val selectedOption = remember { mutableStateOf(radioOptions.first()) }

    when (sortedByState) {
        SortingTypes.ABC -> selectedOption.value = abcType
        SortingTypes.VALUE -> selectedOption.value = valueType
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(start = 18.dp, end = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpacingVertical(heightDp = 24)
        Text(
            text = stringResource(R.string.bottom_sheet_sort_title),
            style = Paragraph_16_Medium, color = MaterialTheme.colors.onBackground
        )
        SpacingVertical(heightDp = 36)
        radioOptions.forEach { text ->
            val onClick = {
                selectedOption.value = text
                sortByTypeClick(
                    when (text) {
                        abcType -> SortingTypes.ABC
                        valueType -> SortingTypes.VALUE
                        else -> SortingTypes.ABC
                    },
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                RadioButtonCustom(
                    selected = text == selectedOption.value,
                    onClick = onClick,
                )
                SpacingHorizontal(WidthDp = 14)
                Text(text = text, style = Paragraph_16, color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.clickable {
                        onClick()
                    }
                )
            }
        }
        SpacingVertical(heightDp = 55)
    }
}


