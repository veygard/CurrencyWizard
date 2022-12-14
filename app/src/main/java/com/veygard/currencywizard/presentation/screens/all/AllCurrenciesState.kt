package com.veygard.currencywizard.presentation.screens.all

import com.veygard.currencywizard.domain.model.Currency

sealed class AllCurrenciesState {
    object ConnectionError: AllCurrenciesState()
    object Loading: AllCurrenciesState()
    object ListError: AllCurrenciesState()
    object NoLocalDb: AllCurrenciesState()
    data class CurrencyListReady(val list: List<Currency>): AllCurrenciesState()
}