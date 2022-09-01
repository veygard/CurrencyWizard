package com.veygard.currencywizzard.data.network.model.currencies.fetch

import com.veygard.currencywizzard.data.network.model.currencies.Currency

data class FetchApiResponse(
    val base: String? = null,
    val ms: Int? = null,
    val results: List<Currency>  ? = null,
    val updated: String  ? = null
)