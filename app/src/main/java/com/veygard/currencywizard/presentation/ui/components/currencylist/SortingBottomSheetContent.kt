package com.veygard.currencywizard.presentation.ui.components.currencylist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.veygard.currencywizard.R
import com.veygard.currencywizard.presentation.model.SortingTypes
import com.veygard.currencywizard.presentation.ui.Paragraph_16
import com.veygard.currencywizard.presentation.ui.Paragraph_16_Medium
import com.veygard.currencywizard.presentation.ui.SpacingHorizontal
import com.veygard.currencywizard.presentation.ui.SpacingVertical
import com.veygard.currencywizard.presentation.ui.components.RadioButtonCustom

@Composable
fun SortingBottomSheetContent(
    sortByTypeClick: (type: SortingTypes) -> Unit,
    sortedByState: SortingTypes
) {
    val abcType = stringResource(R.string.radio_button_abc)
    val valueType = stringResource(R.string.radio_button_value)
    val radioOptions = listOf(abcType, valueType)
    val selectedOption = remember { mutableStateOf(radioOptions.first()) }

    when (sortedByState) {
        SortingTypes.ABC -> selectedOption.value = abcType
        SortingTypes.VALUE -> selectedOption.value = valueType
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(start = 18.dp, end = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpacingVertical(heightDp = 24)
        Text(
            text = stringResource(R.string.bottom_sheet_sort_title),
            style = Paragraph_16_Medium, color = MaterialTheme.colors.onBackground
        )
        SpacingVertical(heightDp = 36)
        radioOptions.forEach { text ->
            val onClick = {
                selectedOption.value = text
                sortByTypeClick(
                    when (text) {
                        abcType -> SortingTypes.ABC
                        valueType -> SortingTypes.VALUE
                        else -> SortingTypes.ABC
                    },
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                RadioButtonCustom(
                    selected = text == selectedOption.value,
                    onClick = onClick,
                )
                SpacingHorizontal(WidthDp = 14)
                Text(text = text, style = Paragraph_16, color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.clickable {
                        onClick()
                    }
                )
            }
        }
        SpacingVertical(heightDp = 55)
    }
}
