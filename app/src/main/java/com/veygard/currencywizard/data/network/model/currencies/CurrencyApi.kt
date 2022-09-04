package com.veygard.currencywizard.data.network.model.currencies

import com.veygard.currencywizard.data.local.CurrencyEntity
import com.veygard.currencywizard.domain.model.Currency

data class CurrencyApi(val name: String, val value: String, var isFavorite: Boolean?= null, var flagId: Int? = null)

fun CurrencyApi.toEntity() =
    CurrencyEntity(abbreviation = name, descriptionName = value, isFavorite = isFavorite ?: false, flagId = 0)

fun List<CurrencyApi>.toEntityList() = map { it.toEntity() }

fun CurrencyApi.toStuffed(value:String, isFavorite: Boolean, flagId: Int, descriptionName: String) =
    Currency(
        abbreviation = name,
        value = value,
        descriptionName = descriptionName,
        flagId = flagId,
        isFavorite = isFavorite
    )
