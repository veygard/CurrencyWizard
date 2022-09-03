package com.veygard.currencywizzard.presentation.screens.all

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.murgupluoglu.flagkit.FlagKit
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.veygard.currencywizzard.presentation.screens.destinations.ErrorScreenDestination
import com.veygard.currencywizzard.presentation.screens.destinations.StartScreenDestination

@ExperimentalMaterialApi
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
            MainScreenContent()
        }
        AllCurrenciesState.NoLocalDb -> navigator.navigate(StartScreenDestination)
        null -> {}
    }

}

@ExperimentalMaterialApi
@Composable
fun MainScreenContent() {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        sheetContent = {},
        sheetPeekHeight = 0.dp,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(start = 8.dp, end = 8.dp),
        sheetElevation = 8.dp,
        scaffoldState = bottomSheetScaffoldState,
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        content = {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                backgroundColor = MaterialTheme.colors.background,
                
                bottomBar = {
                    val flagId = FlagKit.getResId(LocalContext.current, "kz")
                    Column(modifier = Modifier.fillMaxSize()) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(flagId)
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(com.veygard.currencywizzard.R.drawable.ic_baseline_fireplace_24),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(36.dp),
                            error = painterResource(com.veygard.currencywizzard.R.drawable.ic_baseline_fireplace_24),
                        )
                    }
                }
            ) {
            }
        }
    )
}
