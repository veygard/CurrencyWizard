package com.veygard.currencywizard.data.network.model.currencies

import com.veygard.currencywizard.data.local.CurrencyEntity

data class Currency(val name:String, val value: String)

fun Currency.toEntity(isFavorite: Boolean) =
    CurrencyEntity(abbreviation = name, descriptionName = value, isFavorite = isFavorite)

fun List<Currency>.toEntityList() = map{ it.toEntity(false)}