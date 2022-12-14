package com.veygard.currencywizard.domain.model

data class Currency(
    val abbreviation: String,
    val value: String?,
    var descriptionName: String,
    var isFavorite: Boolean,
    val flagId: Int
)