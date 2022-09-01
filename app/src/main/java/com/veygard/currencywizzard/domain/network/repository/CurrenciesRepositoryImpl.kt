package com.veygard.currencywizzard.domain.network.repository

import com.veygard.currencywizzard.data.network.api.CurrenciesConvertApi
import com.veygard.currencywizzard.data.network.api.CurrenciesFetchApi
import com.veygard.currencywizzard.data.network.api.CurrenciesGetAllApi
import com.veygard.currencywizzard.data.network.model.currencies.toEntityList
import com.veygard.currencywizzard.domain.local.repository.LocalCurrenciesRepository
import com.veygard.currencywizzard.domain.network.response.CurrenciesConvertRepoResponse
import com.veygard.currencywizzard.domain.network.response.CurrenciesFetchRepoResponse
import com.veygard.currencywizzard.domain.network.response.CurrenciesGetAllRepoResponse
import com.veygard.currencywizzard.domain.network.response.CurrenciesRepoResponse

class CurrenciesRepositoryImpl(
    private val currenciesFetchApi: CurrenciesFetchApi,
    private val currenciesGetAllApi: CurrenciesGetAllApi,
    private val currenciesConvertApi: CurrenciesConvertApi,
    private val localDbRepository: LocalCurrenciesRepository
) : CurrenciesRepository {

    override suspend fun fetchAll(from: String): CurrenciesRepoResponse {
        return try {
            val call = currenciesFetchApi.fetchAll(from)
            when {
                call.isSuccessful -> {
                    call.body()?.let {
                        CurrenciesFetchRepoResponse.SuccessFetch(it)
                    } ?: CurrenciesFetchRepoResponse.Error
                }
                else -> CurrenciesFetchRepoResponse.Error
            }
        } catch (e: Exception) {
            CurrenciesGetAllRepoResponse.Error
        }
    }

    override suspend fun fetchMulti(from: String, to: String): CurrenciesRepoResponse {
        return try {
            val call = currenciesFetchApi.fetchMulti(from = from, to = to)
            when {
                call.isSuccessful -> {
                    call.body()?.let {
                        CurrenciesFetchRepoResponse.SuccessFetch(it)
                    } ?: CurrenciesFetchRepoResponse.Error
                }
                else -> CurrenciesFetchRepoResponse.Error
            }
        } catch (e: Exception) {
            CurrenciesGetAllRepoResponse.Error
        }
    }

    override suspend fun getAllCurrencies(): CurrenciesRepoResponse {
        return try {
            val call = currenciesGetAllApi.getAll()
            when {
                call.isSuccessful -> {
                    call.body()?.let {
                        localDbRepository.insertAllCurrencies(it.currencies?.toEntityList() ?: throw Exception())
                        CurrenciesGetAllRepoResponse.SuccessGetAll(it)
                    } ?: CurrenciesGetAllRepoResponse.Error
                }
                else -> CurrenciesGetAllRepoResponse.Error
            }
        } catch (e: Exception) {
            CurrenciesGetAllRepoResponse.Error
        }
    }

    override suspend fun convertCurrency(
        from: String,
        to: String,
        amount: Double
    ): CurrenciesRepoResponse {
        return try {
            val call = currenciesConvertApi.convertApi(from, to, amount)
            when {
                call.isSuccessful -> {
                    call.body()?.let {
                        CurrenciesConvertRepoResponse.SuccessConvert(it)
                    } ?: CurrenciesGetAllRepoResponse.Error
                }
                else -> CurrenciesGetAllRepoResponse.Error
            }
        } catch (e: Exception) {
            CurrenciesGetAllRepoResponse.Error
        }
    }

}