package com.veygard.currencywizard.presentation.screens.start

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.veygard.currencywizard.R
import com.veygard.currencywizard.presentation.screens.destinations.AllCurrenciesScreenDestination
import com.veygard.currencywizard.presentation.screens.destinations.ErrorScreenDestination
import com.veygard.currencywizard.presentation.ui.SpacingVertical

@Composable
@Destination(start = true)
fun StartScreen(
    navigator: DestinationsNavigator,
    viewModel: StartScreenViewModel = hiltViewModel(),
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_anim))
    val progress by animateLottieCompositionAsState(composition)
    val state = viewModel.stateFlow.collectAsState()
    LaunchedEffect(key1 = Unit, block = {
        viewModel.getAllCurrencies()
    })
    when(state.value){
        StartScreenState.Loading -> {
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = CenterHorizontally) {
                SpacingVertical(heightDp = 24)
                Text(text = "We are starting...", fontSize = 32.sp)
                LottieAnimation(composition = composition, progress = { progress})
            }
        }
        StartScreenState.ConnectionError  -> navigator.navigate(ErrorScreenDestination)
        StartScreenState.Success  -> navigator.navigate(AllCurrenciesScreenDestination)
        null -> {}
    }
}