package com.veygard.currencywizard.presentation.screens.all

import com.veygard.currencywizard.data.network.model.currencies.Currency
import com.veygard.currencywizard.domain.model.CurrencyStuffed

sealed class AllCurrenciesState {
    object ConnectionError: AllCurrenciesState()
    object Loading: AllCurrenciesState()
    object ListError: AllCurrenciesState()
    object NoLocalDb: AllCurrenciesState()
    data class CurrencyListReady(val list: List<CurrencyStuffed>): AllCurrenciesState()
}