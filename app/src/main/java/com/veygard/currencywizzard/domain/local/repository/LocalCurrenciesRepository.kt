package com.veygard.currencywizzard.domain.local.repository

import com.veygard.currencywizzard.data.local.CurrencyEntity

interface LocalCurrenciesRepository {
    suspend fun insertCurrency(currencyEntity: CurrencyEntity)

    suspend fun insertAllCurrencies(list: List<CurrencyEntity>)

    suspend fun updateCurrencyByAbbreviation(
        abbreviation: String,
        isFavorite: Boolean,
        descriptionName: String? = null
    )

    suspend fun getAllCurrenciesDao(): List<CurrencyEntity>

    suspend fun getCurrencyByDescription(descriptionName: String)  : CurrencyEntity?

    suspend fun geCurrencyByAbbreviation(abbreviation: String) : CurrencyEntity?

    suspend fun getFavoriteCurrencies(isFavorite: Boolean = true): List<CurrencyEntity>?
}