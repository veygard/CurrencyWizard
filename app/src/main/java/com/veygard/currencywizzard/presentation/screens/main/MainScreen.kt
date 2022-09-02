package com.veygard.currencywizzard.presentation.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.murgupluoglu.flagkit.FlagKit
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.veygard.currencywizzard.R
import com.veygard.currencywizzard.presentation.screens.destinations.ErrorScreenDestination

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

    when (screenState.value) {
        MainScreenState.ConnectionError -> navigator.navigate(ErrorScreenDestination)
        MainScreenState.ListError -> navigator.navigate(ErrorScreenDestination)
        MainScreenState.Loading -> CircularProgressIndicator()
        is MainScreenState.CurrencyListReady -> {
            MainScreenContent()
        }
        null -> {}
    }

}

@Composable
fun MainScreenContent() {
    val flagId = FlagKit.getResId(LocalContext.current, "kz")
    val o = 6
    Column(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(flagId)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_baseline_fireplace_24),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(36.dp),
            error = painterResource(R.drawable.ic_baseline_fireplace_24),
        )
 
        NavigationBar() {

        }
    }
}
