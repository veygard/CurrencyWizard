package com.veygard.currencywizard.presentation.screens.favorite

import com.veygard.currencywizard.domain.model.Currency

sealed class FavoriteCurrenciesState {
    object ConnectionError: FavoriteCurrenciesState()
    object Loading: FavoriteCurrenciesState()
    object FavoritesEmpty: FavoriteCurrenciesState()
    object ListError: FavoriteCurrenciesState()
    data class CurrencyListReady(val list: List<Currency>): FavoriteCurrenciesState()
}