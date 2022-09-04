package com.veygard.currencywizard.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.veygard.currencywizard.R
import com.veygard.currencywizard.domain.model.Currency
import com.veygard.currencywizard.presentation.ui.*

@Composable
fun CurrencyListCompose(
    currencies: List<Currency>,
    onFavoriteClick: (String, Boolean) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background),
        contentPadding = PaddingValues(vertical = 24.dp)
    ) {
        items(currencies.size, itemContent = { index ->
            CurrencyItem(currency = currencies[index], onFavoriteClick)
            SpacingVertical(heightDp = 16)
        })
    }
}

@Composable
private fun CurrencyItem(currency: Currency, onFavoriteClick: (String, Boolean) -> Unit) {

    val favoriteState = remember { mutableStateOf(currency.isFavorite) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = Margin.horizontalStandard, end = Margin.horizontalStandard),
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(modifier = Modifier.weight(2f), verticalAlignment = Alignment.CenterVertically) {
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
                        .size(36.dp),
                    error = painterResource(R.drawable.ic_baseline_outlined_flag_24),
                )
                SpacingHorizontal(WidthDp = 12)
                Column() {
                    Text(
                        text = currency.abbreviation,
                        style = Paragraph_16_Medium,
                        color = MaterialTheme.colors.onBackground
                    )
                    SpacingVertical(heightDp = 8)
                    Text(
                        text = currency.descriptionName,
                        style = bottomBarTextStyle,
                        color = MaterialTheme.colors.onBackground
                    )
                }
            }
            SpacingHorizontal(WidthDp = 8)
            Text(
                text = currency.value ?: stringResource(id = R.string.no_info),
                style = Paragraph_16_longread,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.weight(1f)
            )
            Box(modifier = Modifier.weight(0.5f), contentAlignment = Alignment.CenterEnd) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(if ((favoriteState.value)) R.drawable.ic_baseline_star_24 else R.drawable.ic_baseline_star_border_24)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_baseline_star_border_24),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            favoriteState.value = !favoriteState.value
                            onFavoriteClick(currency.abbreviation, favoriteState.value)
                        }
                        .size(36.dp),
                    error = painterResource(R.drawable.ic_baseline_star_border_24),
                )

            }
        }
    }
}