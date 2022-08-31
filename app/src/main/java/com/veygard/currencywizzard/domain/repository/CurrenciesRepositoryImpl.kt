package com.veygard.currencywizzard.domain.repository

import com.veygard.currencywizzard.data.network.CurrenciesApi
import com.veygard.currencywizzard.data.network.model.FetchApiResponse
import com.veygard.currencywizzard.data.network.model.testing.Results
import com.veygard.currencywizzard.domain.response.CurrenciesRepoResponse

class CurrenciesRepositoryImpl(private val currenciesApi: CurrenciesApi) : CurrenciesRepository {

    override suspend fun fetchAll(from: String): CurrenciesRepoResponse {
        val call  = currenciesApi.fetchAll(from)
        return when{
            call.isSuccessful -> {
                call.body()?.let {
                    CurrenciesRepoResponse.SuccessFetch(FetchApiResponse())
                } ?: CurrenciesRepoResponse.Error
            }
            else  -> CurrenciesRepoResponse.Error
        }
    }

    override suspend fun fetchMulti(from: String, to: String): CurrenciesRepoResponse {
        val call  = currenciesApi.fetchMulti(from= from, to= to)
        return when{
            call.isSuccessful -> {
                call.body()?.let {
                    val map = mutableMapOf<String, String>()
                    for (prop in Results::class.members) {
                        map.put(prop.)
                    }
                    CurrenciesRepoResponse.SuccessFetch(FetchApiResponse())
                } ?: CurrenciesRepoResponse.Error
            }
            else  -> CurrenciesRepoResponse.Error
        }
    }
}