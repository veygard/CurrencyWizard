package com.veygard.currencywizzard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.veygard.currencywizzard.presentation.viewmodel.CurrenciesViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: CurrenciesViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
        val state  = viewModel.stateFlow.collectAsState()
            
            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = state.value?.size?.toString() ?: "empty", color = MaterialTheme.colors.onBackground, style = H_L3)
            }
        }
    }
}

val H_L3 = TextStyle(
    fontFamily = FontFamily.Default,
    fontSize = 28.sp,
    fontWeight = FontWeight.W700,
    lineHeight = 36.sp,
    textAlign = TextAlign.Start,
)