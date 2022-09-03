package com.veygard.currencywizzard.presentation.screens.all

import com.veygard.currencywizzard.data.network.model.currencies.Currency

sealed class AllCurrenciesState {
    object ConnectionError: AllCurrenciesState()
    object Loading: AllCurrenciesState()
    object ListError: AllCurrenciesState()
    object NoLocalDb: AllCurrenciesState()
    data class CurrencyListReady(val list: List<Currency>): AllCurrenciesState()
}