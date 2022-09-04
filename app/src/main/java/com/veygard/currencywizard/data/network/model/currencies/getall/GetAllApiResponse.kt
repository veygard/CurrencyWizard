package com.veygard.currencywizard.data.network.model.currencies.getall

import com.veygard.currencywizard.data.network.model.currencies.CurrencyApi

data class GetAllApiResponse(
    val ms: Int? = null,
    val currencies: List<CurrencyApi>  ? = null,
)