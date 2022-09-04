package com.veygard.currencywizard.presentation.ui.components.autocomplite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.veygard.currencywizard.R
import com.veygard.currencywizard.domain.model.Currency
import com.veygard.currencywizard.presentation.ui.*


@Composable
fun SearchBarWithAutoComplete(
    modifier: Modifier = Modifier,
    totalList: List<Currency>,
    onCurrencyClick: (String) -> Unit,
    showSearchBarState: MutableState<Boolean>
) {
    val openState = remember { mutableStateOf(false) }
    val searchValue = remember { mutableStateOf("") }
    val originalList: List<Currency> = totalList.sortedBy { it.abbreviation }
    val autoCompileList: MutableState<MutableList<Currency>> =
        remember { mutableStateOf(originalList.toMutableList()) }

    val errorState = remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        OutlinedTextField(
            isError = errorState.value,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    if (it.isFocused) openState.value = true
                },
            onValueChange = { query ->
                errorState.value = false
                openState.value = true
                searchValue.value = query
                getListBySearch(query, autoCompileList, originalList)
            },
            label = { Text(text = "Search") },
            value = searchValue.value,
            textStyle = Paragraph_16,
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = {
                    searchValue.value = ""
                    openState.value = false
                    showSearchBarState.value = false
                    getListBySearch(searchValue.value, autoCompileList, originalList)
                }) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
                }
            },
            keyboardActions = KeyboardActions(onDone = {
                val currency = getCurrencyByQuery(searchValue.value, originalList)
                currency?.let {
                    openState.value = false
                    showSearchBarState.value = false
                    onCurrencyClick(it.abbreviation)
                } ?: run {
                    errorState.value = true
                }
            }),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            )
        )
        if (openState.value) {
            SpacingVertical(heightDp = 4)
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(buttonShapes.large)
                    .padding(start = Margin.horizontalStandard, end = Margin.horizontalStandard)
                    .background(MaterialTheme.colors.onSecondary),
                contentPadding = PaddingValues(vertical = 24.dp)
            ) {
                items(autoCompileList.value.size, itemContent = { index ->
                    SearchItem(
                        currency = autoCompileList.value[index],
                        openState = openState,
                        showSearchBarState = showSearchBarState,
                        onClickName = onCurrencyClick
                    )
                    SpacingVertical(heightDp = 12)
                })
            }
        }
    }
}

@Composable
private fun SearchItem(
    currency: Currency,
    openState: MutableState<Boolean>,
    showSearchBarState: MutableState<Boolean>,
    onClickName: (String) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                openState.value = false
                showSearchBarState.value = false
                onClickName(currency.abbreviation)
            }
            .padding(start = Margin.horizontalStandard, end = Margin.horizontalStandard)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(currency.flagId)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_baseline_outlined_flag_24),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(24.dp),
            error = painterResource(R.drawable.ic_baseline_outlined_flag_24),
        )
        SpacingHorizontal(WidthDp = 14)
        Text(text = currency.abbreviation, style = Paragraph_16)
    }
}

private fun getListBySearch(
    query: String,
    list: MutableState<MutableList<Currency>>,
    original: List<Currency>
) {
    val filteredList = original.filter { it.abbreviation.contains(query.uppercase()) }
    list.value = filteredList.toMutableList()
}

private fun getCurrencyByQuery(query: String, original: List<Currency>): Currency? {
    return original.singleOrNull { it.abbreviation == query.uppercase() }
}