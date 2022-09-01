package com.veygard.currencywizzard.presentation.screens.main

import com.veygard.currencywizzard.data.network.model.currencies.Currency

sealed class MainScreenState {
    object ConnectionError: MainScreenState()
    object Loading: MainScreenState()
    object ListError: MainScreenState()
    data class CurrencyListReady(val list: List<Currency>): MainScreenState()
}