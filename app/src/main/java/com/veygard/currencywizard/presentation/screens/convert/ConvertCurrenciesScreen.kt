package com.veygard.currencywizard.presentation.screens.convert

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.murgupluoglu.flagkit.FlagKit
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.veygard.currencywizard.R
import com.veygard.currencywizard.domain.model.Currency
import com.veygard.currencywizard.presentation.navigation.BottomBarScreen
import com.veygard.currencywizard.presentation.navigation.provideBottomBarScreenList
import com.veygard.currencywizard.presentation.screens.destinations.ErrorScreenDestination
import com.veygard.currencywizard.presentation.ui.*
import com.veygard.currencywizard.presentation.ui.components.BottomBar
import com.veygard.currencywizard.presentation.ui.components.CommonButton
import com.veygard.currencywizard.presentation.ui.components.autocomplite.CurrencyButtonWithAutoComplete

@Composable
@Destination
fun ConvertCurrenciesScreen(
    navigator: DestinationsNavigator,
    viewModel: ConvertCurrenciesViewModel = hiltViewModel(),
) {
    val screenState = viewModel.stateFlow.collectAsState()
    val pickedCurrency = viewModel.pickedCurrency.collectAsState()
    val convertToCurrency = viewModel.convertToCurrency.collectAsState()
    val amountConvert = remember { mutableStateOf("1000") }
    val amountError = remember { mutableStateOf(false) }

    val onCurrencyFromClick: (String) -> Unit = { currency ->
        viewModel.updatePickedCurrency(currency)
    }
    val onCurrencyToClick: (String) -> Unit = { currency ->
        viewModel.updateConvertToCurrency(currency)
    }
    val onConvertClick: () -> Unit = {
        if (!amountConvert.value.isDigitsOnly()) amountError.value = true
        else viewModel.convert(amountConvert.value.toDouble())
    }

    val clearConvert: () -> Unit = {
        viewModel.clearConvert()
    }


    when (screenState.value) {
        ConvertCurrenciesState.ConnectionError, ConvertCurrenciesState.EmptyLocalDb -> navigator.navigate(
            ErrorScreenDestination
        )
        ConvertCurrenciesState.BeforeConvert -> ConvertCurrenciesScreenContent(
            navigator,
            pickedCurrency,
            convertToCurrency,
            viewModel.totalList.value,
            onCurrencyFromClick,
            onCurrencyToClick,
            amountConvert,
            amountError,
            onConvertClick
        )
        is ConvertCurrenciesState.ConvertingComplete -> ConvertCompleteContent(
            navigator,
            clearConvert,
            screenState.value as ConvertCurrenciesState.ConvertingComplete
        )
        else -> {}
    }
}

@Composable
fun ConvertCompleteContent(
    navigator: DestinationsNavigator,
    clearConvert: () -> Unit,
    results: ConvertCurrenciesState.ConvertingComplete
) {
    Scaffold(
        bottomBar = {
            BottomBar(
                navigator,
                provideBottomBarScreenList(),
                BottomBarScreen.Convert
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = Margin.horizontalStandard, end = Margin.horizontalStandard),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.convert_currency_title_result),
                style = H_L4,
                textAlign = TextAlign.Center
            )
            SpacingVertical(heightDp = 24)

            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.width(120.dp),
                    text = results.amount.toString() + " " + results.from
                )
                SpacingHorizontal(WidthDp = 16)
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(
                            FlagKit.getDrawable(
                                LocalContext.current,
                                results.from.substring(0, 2)
                            )
                        )
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_baseline_outlined_flag_24),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(36.dp),
                    error = painterResource(R.drawable.ic_baseline_outlined_flag_24),
                )
            }
            SpacingVertical(heightDp = 8)
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.exchange)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_baseline_outlined_flag_24),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp),
                    error = painterResource(R.drawable.ic_baseline_outlined_flag_24),
                )
            }
            SpacingVertical(heightDp = 8)
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.width(120.dp),
                    text = results.exchangeValue.toString() + " " + results.to
                )
                SpacingHorizontal(WidthDp = 16)
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(FlagKit.getDrawable(LocalContext.current, results.to.substring(0, 2)))
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_baseline_outlined_flag_24),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(36.dp),
                    error = painterResource(R.drawable.ic_baseline_outlined_flag_24),
                )
            }


            SpacingVertical(heightDp = 36)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                CommonButton(label = stringResource(id = R.string.convert_currency_again_button)) {
                    clearConvert()
                }
            }
        }
    }
}

@Composable
private fun ConvertCurrenciesScreenContent(
    navigator: DestinationsNavigator,
    pickedCurrency: State<String?>,
    convertToCurrency: State<String?>,
    totalCurrencies: List<Currency>?,
    onCurrencyFromClick: (String) -> Unit,
    onCurrencyToClick: (String) -> Unit,
    amountConvert: MutableState<String>,
    amountError: MutableState<Boolean>,
    onConvertClick: () -> Unit
) {

    Scaffold(
        bottomBar = {
            BottomBar(
                navigator,
                provideBottomBarScreenList(),
                BottomBarScreen.Convert
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = Margin.horizontalStandard, end = Margin.horizontalStandard)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.convert_currency_title),
                style = H_L4,
                textAlign = TextAlign.Center
            )
            SpacingVertical(heightDp = 4)
            Row(
                Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.convert_currency_from_title)
                )
                SpacingHorizontal(WidthDp = 8)
                CurrencyButtonWithAutoComplete(
                    modifier = Modifier
                        .weight(3f),
                    totalList = totalCurrencies,
                    onCurrencyClick = onCurrencyFromClick,
                    pickedCurrency = pickedCurrency
                )
            }
            SpacingVertical(heightDp = 16)
            Row(
                Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.convert_currency_amount)
                )
                SpacingHorizontal(WidthDp = 8)
                OutlinedTextField(
                    modifier = Modifier.weight(3f),
                    isError = amountError.value,
                    value = amountConvert.value,
                    onValueChange = { query ->
                        if (query.isDigitsOnly()) {
                            amountError.value = false
                            amountConvert.value = query
                        } else {
                            amountError.value = true
                        }
                    },
                    textStyle = Paragraph_16,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.NumberPassword
                    )
                )
            }
            SpacingVertical(heightDp = 16)
            Row(
                Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.convert_currency_to)
                )
                SpacingHorizontal(WidthDp = 8)
                CurrencyButtonWithAutoComplete(
                    modifier = Modifier
                        .weight(3f),
                    totalList = totalCurrencies,
                    onCurrencyClick = onCurrencyToClick,
                    pickedCurrency = convertToCurrency
                )
            }
            SpacingVertical(heightDp = 16)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                CommonButton(label = stringResource(id = R.string.convert_currency_button)) {
                    onConvertClick()
                }
            }
        }
    }
}
