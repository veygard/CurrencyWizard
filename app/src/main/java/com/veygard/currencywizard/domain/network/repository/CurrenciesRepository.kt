package com.veygard.currencywizard.domain.network.repository

import com.veygard.currencywizard.domain.network.response.CurrenciesRepoResponse

interface CurrenciesRepository {
    suspend fun fetchAll(from:String): CurrenciesRepoResponse
    suspend fun fetchMulti(from:String, to: String): CurrenciesRepoResponse
    suspend fun getAllCurrencies(): CurrenciesRepoResponse
    suspend fun convertCurrency(from: String, to: String, amount: Double):  CurrenciesRepoResponse
}