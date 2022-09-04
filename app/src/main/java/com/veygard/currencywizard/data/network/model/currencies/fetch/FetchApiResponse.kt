package com.veygard.currencywizard.data.network.model.currencies.fetch

import com.veygard.currencywizard.data.network.model.currencies.CurrencyApi

data class FetchApiResponse(
    val base: String? = null,
    val ms: Int? = null,
    val results: List<CurrencyApi>  ? = null,
    val updated: String  ? = null
)