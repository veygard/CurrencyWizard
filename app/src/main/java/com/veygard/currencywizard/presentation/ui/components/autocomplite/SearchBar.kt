package com.veygard.currencywizard.presentation.ui.components.autocomplite

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.veygard.currencywizard.data.local.CurrencyEntity
import com.veygard.currencywizard.presentation.ui.Paragraph_16
import com.veygard.currencywizard.presentation.ui.SpacingVertical


@Composable
fun TextSearchBarWithAutoComplete(
    modifier: Modifier = Modifier,
    list: List<CurrencyEntity>,
    onClickName: (String) -> Unit
) {
    val openState = remember { mutableStateOf(false) }
    val searchValue = remember { mutableStateOf("") }
    val autoCompileList: MutableState<MutableList<String>> =
        remember { mutableStateOf(list.map { it.abbreviation }.toMutableList()) }
    val originalList: List<String> = list.map { it.abbreviation }


    Column() {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged {
                    if (it.isFocused) openState.value = true
                },
            onValueChange = { query ->
                searchValue.value = query
                getListBySearch(query, autoCompileList, originalList)
            },
            label = { Text(text = "Search") },
            value = searchValue.value,
            textStyle = Paragraph_16,
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
                }
            },
            keyboardActions = KeyboardActions(onDone = { }),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            )
        )
        if (openState.value) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background),
                contentPadding = PaddingValues(vertical = 24.dp)
            ) {
                items(autoCompileList.value.size, itemContent = { index ->
                    SearchItem(name = autoCompileList.value[index], onClickName)
                    SpacingVertical(heightDp = 4)
                })
            }
        }
    }
}

@Composable
private fun SearchItem(name: String, onClickName: (String) -> Unit) {
    Text(text = name, style = Paragraph_16, modifier = Modifier.clickable {
        onClickName(name)
    })
}

private fun getListBySearch(
    query: String,
    list: MutableState<MutableList<String>>,
    original: List<String>
) {
    val filteredList = original.filter { it.contains(query.uppercase()) }
    list.value = filteredList.toMutableList()
}