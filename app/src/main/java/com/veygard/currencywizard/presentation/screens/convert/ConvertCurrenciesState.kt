package com.veygard.currencywizard.presentation.screens.convert

sealed class ConvertCurrenciesState {
    object ConnectionError : ConvertCurrenciesState()
    object Loading : ConvertCurrenciesState()
    object EmptyLocalDb : ConvertCurrenciesState()
    object BeforeConvert : ConvertCurrenciesState()
    data class ConvertingComplete(
        val exchangeValue: Double,
        val amount: Double,
        val rate: Double,
        val from: String,
        val to: String
    ) : ConvertCurrenciesState()
}