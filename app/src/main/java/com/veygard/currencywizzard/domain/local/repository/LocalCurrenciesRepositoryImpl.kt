package com.veygard.currencywizzard.domain.local.repository

import com.veygard.currencywizzard.data.local.CurrenciesDao
import com.veygard.currencywizzard.data.local.CurrencyEntity
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

    override suspend fun getAllCurrenciesDao(): List<CurrencyEntity> =
        currenciesDao.getAllCurrenciesDao()

    override suspend fun getCurrencyByDescription(descriptionName: String): CurrencyEntity? =
        currenciesDao.getCurrencyByDescription(descriptionName)

    override suspend fun geCurrencyByAbbreviation(abbreviation: String): CurrencyEntity? =
        currenciesDao.geCurrencyByAbbreviation(abbreviation)

    override suspend fun getFavoriteCurrencies(isFavorite: Boolean): List<CurrencyEntity>? =
        currenciesDao.getFavoriteCurrencies(isFavorite)
}