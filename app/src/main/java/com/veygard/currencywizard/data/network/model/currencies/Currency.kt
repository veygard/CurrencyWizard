package com.veygard.currencywizard.data.network.model.currencies

import com.veygard.currencywizard.data.local.CurrencyEntity
import com.veygard.currencywizard.domain.model.CurrencyStuffed

data class Currency(val name: String, val value: String)

fun Currency.toEntity(isFavorite: Boolean) =
    CurrencyEntity(abbreviation = name, descriptionName = value, isFavorite = isFavorite)

fun List<Currency>.toEntityList() = map { it.toEntity(false) }

fun Currency.toStuffed(value:String, isFavorite: Boolean, flagId: Int, descriptionName: String) =
    CurrencyStuffed(
        abbreviation = name,
        value = value,
        descriptionName = descriptionName,
        flagId = flagId,
        isFavorite = isFavorite
    )
