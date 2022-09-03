package com.veygard.currencywizard.data.network.model.currencies

import com.veygard.currencywizard.data.local.CurrencyEntity
import com.veygard.currencywizard.domain.model.CurrencyStuffed

data class Currency(val name: String, val value: String, var isFavorite: Boolean?= null)

fun Currency.toEntity() =
    CurrencyEntity(abbreviation = name, descriptionName = value, isFavorite = isFavorite ?: false)

fun List<Currency>.toEntityList() = map { it.toEntity() }

fun Currency.toStuffed(value:String, isFavorite: Boolean, flagId: Int, descriptionName: String) =
    CurrencyStuffed(
        abbreviation = name,
        value = value,
        descriptionName = descriptionName,
        flagId = flagId,
        isFavorite = isFavorite
    )
