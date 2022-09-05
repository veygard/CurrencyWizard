package com.veygard.currencywizard.presentation.screens.error

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.veygard.currencywizard.R
import com.veygard.currencywizard.presentation.screens.destinations.AllCurrenciesScreenDestination
import com.veygard.currencywizard.presentation.ui.H_L3
import com.veygard.currencywizard.presentation.ui.Margin
import com.veygard.currencywizard.presentation.ui.Paragraph_16_Medium
import com.veygard.currencywizard.presentation.ui.SpacingVertical
import com.veygard.currencywizard.presentation.ui.components.CommonButton

@Composable
@Destination
fun ErrorScreen(
    navigator: DestinationsNavigator,
) {
    val context = LocalContext.current
    val connectionError = stringResource(id = R.string.timeout_toast)
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = Margin.horizontalStandard, end = Margin.horizontalStandard)
            .background(MaterialTheme.colors.background),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SpacingVertical(heightDp = 24)
            Icon(
                painter = painterResource(id = R.drawable.ic_no_wifi_svgrepo_com),
                contentDescription = null,
                tint = MaterialTheme.colors.primary,
                modifier = Modifier.size(128.dp)
            )
            SpacingVertical(heightDp = 24.0)
            Text(
                text = stringResource(id = R.string.timeout_title),
                style = H_L3,
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Center
            )
            SpacingVertical(24.0)
            Text(
                text = stringResource(id = R.string.something_wrong_text),
                style = Paragraph_16_Medium,
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Center
            )
            SpacingVertical(24.0)
            CommonButton(label = stringResource(id = R.string.try_again)) {
                if (isInternetAvailable(context)) navigator.navigate(AllCurrenciesScreenDestination)
                else  Toast.makeText(context, connectionError, Toast.LENGTH_LONG).show()
            }
        }
    }
}

private fun isInternetAvailable(context: Context): Boolean {
    var status = false
    context.let {
        val cm =
            it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm.activeNetwork != null && cm.getNetworkCapabilities(cm.activeNetwork) != null) {
            status = true
        }
    }
    return status
}
