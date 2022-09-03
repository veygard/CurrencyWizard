package com.veygard.currencywizard.data.network.model.currencies.convert

data class CurrenciesConvertApiResponse(
    val base: String,
    val amount: Double,
    val result: ConvertCurrenciesResult,
    val ms: Int
)

data class ConvertCurrenciesResult(
    val currencyName: String,
    val value: Double,
    val rate: Double
)