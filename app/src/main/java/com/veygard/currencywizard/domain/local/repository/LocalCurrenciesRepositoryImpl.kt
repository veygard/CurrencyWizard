package com.veygard.currencywizard.domain.local.repository

import com.veygard.currencywizard.data.local.CurrenciesDao
import com.veygard.currencywizard.data.local.CurrencyEntity
import com.veygard.currencywizard.data.local.toCurrency
import com.veygard.currencywizard.data.local.toCurrencyList
import com.veygard.currencywizard.domain.model.Currency
import javax.inject.Inject

class LocalCurrenciesRepositoryImpl @Inject constructor(private val currenciesDao: CurrenciesDao) :
    LocalCurrenciesRepository {
    override suspend fun insertCurrency(currencyEntity: CurrencyEntity) =
        currenciesDao.insertCurrency(currencyEntity)

    override suspend fun insertAllCurrencies(list: List<CurrencyEntity>) =
        currenciesDao.insertAllCurrencies(list)

    override suspend fun updateCurrencyByAbbreviation(
        abbreviation: String,
        isFavorite: Boolean,
        descriptionName: String?
    ) = currenciesDao.updateCurrencyByAbbreviation(
        abbreviation = abbreviation,
        isFavorite = isFavorite,
        descriptionName = descriptionName
    )

    override suspend fun getAllCurrencies(): List<Currency> =
        currenciesDao.getAllCurrenciesDao().toCurrencyList()

    override suspend fun getCurrencyByDescription(descriptionName: String): Currency? =
        currenciesDao.getCurrencyByDescription(descriptionName)?.toCurrency()

    override suspend fun geCurrencyByAbbreviation(abbreviation: String): Currency? =
        currenciesDao.geCurrencyByAbbreviation(abbreviation)?.toCurrency()

    override suspend fun getFavoriteCurrencies(isFavorite: Boolean): List<Currency>? =
        currenciesDao.getFavoriteCurrencies(isFavorite)?.toCurrencyList()
}