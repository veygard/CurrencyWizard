package com.veygard.currencywizard.domain.local.repository

import com.veygard.currencywizard.data.local.CurrencyEntity

interface LocalCurrenciesRepository {
    suspend fun insertCurrency(currencyEntity: CurrencyEntity)

    suspend fun insertAllCurrencies(list: List<CurrencyEntity>)

    suspend fun updateCurrencyByAbbreviation(
        abbreviation: String,
        isFavorite: Boolean,
        descriptionName: String? = null
    )

    suspend fun getAllCurrencies(): List<CurrencyEntity>

    suspend fun getCurrencyByDescription(descriptionName: String)  : CurrencyEntity?

    suspend fun geCurrencyByAbbreviation(abbreviation: String) : CurrencyEntity?

    suspend fun getFavoriteCurrencies(isFavorite: Boolean = true): List<CurrencyEntity>?
}