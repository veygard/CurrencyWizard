package com.veygard.currencywizard.domain.local.repository

import com.veygard.currencywizard.data.local.CurrencyEntity
import com.veygard.currencywizard.domain.model.Currency

interface LocalCurrenciesRepository {
    suspend fun insertCurrency(currencyEntity: CurrencyEntity)

    suspend fun insertAllCurrencies(list: List<CurrencyEntity>)

    suspend fun updateCurrencyByAbbreviation(
        abbreviation: String,
        isFavorite: Boolean,
        descriptionName: String? = null
    )

    suspend fun getAllCurrencies(): List<Currency>

    suspend fun getCurrencyByDescription(descriptionName: String)  : Currency?

    suspend fun geCurrencyByAbbreviation(abbreviation: String) : Currency?

    suspend fun getFavoriteCurrencies(isFavorite: Boolean = true): List<Currency>?
}