@file:OptIn(ExperimentalMaterialApi::class)

package com.veygard.currencywizard.presentation.ui.components.currencylist

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.veygard.currencywizard.R
import com.veygard.currencywizard.domain.model.Currency
import com.veygard.currencywizard.presentation.model.SortingOrder
import com.veygard.currencywizard.presentation.ui.SpacingHorizontal
import com.veygard.currencywizard.presentation.ui.components.autocomplite.SearchBarWithAutoComplete
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CurrencyTopBarContent(
    totalList: List<Currency>?,
    onCurrencyClick: (String) -> Unit,
    pickedCurrency: State<String?>,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    coroutine: CoroutineScope,
    sortByOrderClick: () -> Unit,
    onFavoriteClick: (String, Boolean) -> Unit,
    sortedOrderState: SortingOrder,
) {
    val showSearchBarState = remember { mutableStateOf(false) }
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
                    .fillMaxWidth(),
                totalList = totalList ?: emptyList(),
                onCurrencyClick = onCurrencyClick,
                onFavoriteClick = onFavoriteClick,
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
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colors.primary,
                        backgroundColor = MaterialTheme.colors.primaryVariant
                    )
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
