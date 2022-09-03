package com.veygard.currencywizard.data.local

import androidx.room.*


@Dao
interface CurrenciesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currencyEntity: CurrencyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCurrencies(list: List<CurrencyEntity>)

    @Update
    suspend fun updateCurrency(currencyEntity: CurrencyEntity)

    suspend fun updateCurrencyByAbbreviation(
        abbreviation: String,
        isFavorite: Boolean,
        descriptionName: String? = null
    ) {
        val currencyEntity = geCurrencyByAbbreviation(abbreviation)
        currencyEntity?.isFavorite = isFavorite
        descriptionName?.let { currencyEntity?.descriptionName = it }
        updateCurrency(currencyEntity ?: return)
    }


    @Query("SELECT * FROM currency_table ORDER BY descriptionName DESC")
    suspend fun getAllCurrenciesDao(): List<CurrencyEntity>

    @Query("SELECT * FROM currency_table where descriptionName = :descriptionName")
    suspend fun getCurrencyByDescription(descriptionName: String): CurrencyEntity?

    @Query("SELECT * FROM currency_table where abbreviation = :abbreviation")
    suspend fun geCurrencyByAbbreviation(abbreviation: String): CurrencyEntity?

    @Query("SELECT * FROM currency_table where isFavorite = :isFavorite")
    suspend fun getFavoriteCurrencies(isFavorite: Boolean = true): List<CurrencyEntity>?
}