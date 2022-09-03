package com.veygard.currencywizard.presentation.screens.convert

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.veygard.currencywizard.presentation.navigation.BottomBarScreen
import com.veygard.currencywizard.presentation.navigation.provideBottomBarScreenList
import com.veygard.currencywizard.presentation.ui.components.BottomBar

@Composable
@Destination
fun ConvertCurrenciesScreen(
    navigator: DestinationsNavigator,
//    viewModel: AllCurrenciesViewModel = hiltViewModel(),
) {
//    val screenState = viewModel.stateFlow.collectAsState()
//    LaunchedEffect(key1 = Unit, block = {
//        viewModel.fetchAll()
//    })

//    when (screenState.value) {
//        AllCurrenciesState.ConnectionError -> navigator.navigate(ErrorScreenDestination)
//        AllCurrenciesState.ListError -> navigator.navigate(ErrorScreenDestination)
//        AllCurrenciesState.Loading -> Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) { CircularProgressIndicator() }
//        is AllCurrenciesState.CurrencyListReady -> {
//            AllCurrenciesScreenContent(navigator)
//        }
//        AllCurrenciesState.NoLocalDb -> navigator.navigate(StartScreenDestination)
//        null -> {}
//    }
    ConvertCurrenciesScreenContent(navigator)
}

@Composable
fun ConvertCurrenciesScreenContent(navigator: DestinationsNavigator) {
    Scaffold(
        bottomBar = { BottomBar(navigator, provideBottomBarScreenList(), BottomBarScreen.Convert) }
    ) {
        Text(text = "Convert")
    }
}
