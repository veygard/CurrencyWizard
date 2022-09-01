package com.veygard.currencywizzard.presentation.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.veygard.currencywizzard.presentation.screens.destinations.ErrorScreenDestination
import com.veygard.currencywizzard.presentation.ui.H_L3

@Composable
@Destination(start = true)
fun MainScreen(
    navigator: DestinationsNavigator,
    viewModel: MainScreenViewModel = hiltViewModel(),
) {
    val screenState = viewModel.stateFlow.collectAsState()
    LaunchedEffect(key1 = Unit, block = {
        viewModel.getAllCurrencies()
    })

    when(screenState.value){
        MainScreenState.ConnectionError -> navigator.navigate(ErrorScreenDestination)
        else -> {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "main screen", color = MaterialTheme.colors.onBackground, style = H_L3)
            }
        }
    }

}