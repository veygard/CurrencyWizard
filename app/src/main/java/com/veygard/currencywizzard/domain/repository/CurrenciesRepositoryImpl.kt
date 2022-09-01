package com.veygard.currencywizzard.domain.repository

import com.veygard.currencywizzard.data.network.api.CurrenciesFetchApi
import com.veygard.currencywizzard.data.network.api.CurrenciesGetAllApi
import com.veygard.currencywizzard.data.network.model.currencies.fetch.FetchApiResponse
import com.veygard.currencywizzard.domain.response.CurrenciesRepoResponse

class CurrenciesRepositoryImpl(
    private val currenciesFetchApi: CurrenciesFetchApi,
//    private val currenciesGetAllApi: CurrenciesGetAllApi
) : CurrenciesRepository {

    override suspend fun fetchAll(from: String): CurrenciesRepoResponse {
        val call = currenciesFetchApi.fetchAll(from)

        return when {
            call.isSuccessful -> {
                val map = mutableMapOf<String, String>()
                call.body()?.let { response ->
                    CurrenciesRepoResponse.SuccessFetch(response)
                } ?: CurrenciesRepoResponse.Error
            }
            else -> CurrenciesRepoResponse.Error
        }
    }

    override suspend fun fetchMulti(from: String, to: String): CurrenciesRepoResponse {
        val call = currenciesFetchApi.fetchMulti(from = from, to = to)
        return when {
            call.isSuccessful -> {
                call.body()?.let {
                    val map = mutableMapOf<String, String>()
                    CurrenciesRepoResponse.SuccessFetch(FetchApiResponse())
                } ?: CurrenciesRepoResponse.Error
            }
            else -> CurrenciesRepoResponse.Error
        }
    }

}