package com.veygard.currencywizzard.domain.repository

import com.veygard.currencywizzard.domain.response.CurrenciesFetchRepoResponse
import com.veygard.currencywizzard.domain.response.CurrenciesRepoResponse

interface CurrenciesRepository {
    suspend fun fetchAll(from:String): CurrenciesRepoResponse
    suspend fun fetchMulti(from:String, to: String): CurrenciesRepoResponse
    suspend fun getAllCurrencies(): CurrenciesRepoResponse
    suspend fun convertCurrency(from: String, to: String, amount: Double):  CurrenciesRepoResponse
}