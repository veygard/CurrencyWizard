package com.veygard.currencywizard.data.network.model.currencies.getall

import com.veygard.currencywizard.data.network.model.currencies.Currency

data class GetAllApiResponse(
    val ms: Int? = null,
    val currencies: List<Currency>  ? = null,
)