package com.veygard.currencywizzard.data.network.model.currencies.getall

import com.veygard.currencywizzard.data.network.model.currencies.Currency

data class GetAllApiResponse(
    val ms: Int? = null,
    val currencies: List<Currency>  ? = null,
)