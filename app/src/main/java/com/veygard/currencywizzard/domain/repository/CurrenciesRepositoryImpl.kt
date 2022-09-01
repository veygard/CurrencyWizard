package com.veygard.currencywizzard.domain.repository

import com.veygard.currencywizzard.data.network.api.CurrenciesConvertApi
import com.veygard.currencywizzard.data.network.api.CurrenciesFetchApi
import com.veygard.currencywizzard.data.network.api.CurrenciesGetAllApi
import com.veygard.currencywizzard.data.network.model.currencies.fetch.FetchApiResponse
import com.veygard.currencywizzard.domain.response.CurrenciesFetchRepoResponse
import com.veygard.currencywizzard.domain.response.CurrenciesGetAllRepoResponse
import com.veygard.currencywizzard.domain.response.CurrenciesRepoResponse

class CurrenciesRepositoryImpl(
    private val currenciesFetchApi: CurrenciesFetchApi,
    private val currenciesGetAllApi: CurrenciesGetAllApi,
    private val currenciesConvertApi: CurrenciesConvertApi
) : CurrenciesRepository {

    override suspend fun fetchAll(from: String): CurrenciesRepoResponse {
        val call = currenciesFetchApi.fetchAll(from)

        return when {
            call.isSuccessful -> {
                val map = mutableMapOf<String, String>()
                call.body()?.let { response ->
                    CurrenciesFetchRepoResponse.SuccessFetch(response)
                } ?: CurrenciesFetchRepoResponse.Error
            }
            else -> CurrenciesFetchRepoResponse.Error
        }
    }

    override suspend fun fetchMulti(from: String, to: String): CurrenciesRepoResponse {
        val call = currenciesFetchApi.fetchMulti(from = from, to = to)
        return when {
            call.isSuccessful -> {
                call.body()?.let {
                    val map = mutableMapOf<String, String>()
                    CurrenciesFetchRepoResponse.SuccessFetch(FetchApiResponse())
                } ?: CurrenciesFetchRepoResponse.Error
            }
            else -> CurrenciesFetchRepoResponse.Error
        }
    }

    override suspend fun getAllCurrencies(): CurrenciesRepoResponse {
        return CurrenciesGetAllRepoResponse.Error
    }

    override suspend fun convertCurrency(from: String, to: String, amount: Double):  CurrenciesRepoResponse {
        return CurrenciesGetAllRepoResponse.Error
    }


}