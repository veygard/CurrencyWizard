@file:OptIn(ExperimentalMaterialApi::class)

package com.veygard.currencywizard.presentation.ui.components.autocomplite

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.veygard.currencywizard.domain.model.Currency
import com.veygard.currencywizard.presentation.ui.Margin

@Composable
fun CurrencyButtonWithAutoComplete(
    modifier: Modifier = Modifier,
    totalList: List<Currency>?,
    onCurrencyClick: (String) -> Unit,
    pickedCurrency: State<String?>,
) {
    val showSearchBarState = remember { mutableStateOf(false) }
    Row(modifier = modifier) {
        Column() {
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
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            showSearchBarState.value = true
                        }, elevation = ButtonDefaults.elevation(
                            defaultElevation = 4.dp,
                            pressedElevation = 9.dp,
                            disabledElevation = 2.dp
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colors.primary,
                            backgroundColor = MaterialTheme.colors.primaryVariant
                        )
                    ) {
                        Text(
                            text = pickedCurrency.value ?: "USD",
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

    }
}
