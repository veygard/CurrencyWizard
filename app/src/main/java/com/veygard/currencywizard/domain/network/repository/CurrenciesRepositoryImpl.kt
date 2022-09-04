package com.veygard.currencywizard.domain.network.repository

import android.content.Context
import com.murgupluoglu.flagkit.FlagKit
import com.veygard.currencywizard.data.network.api.CurrenciesConvertApi
import com.veygard.currencywizard.data.network.api.CurrenciesFetchApi
import com.veygard.currencywizard.data.network.api.CurrenciesGetAllApi
import com.veygard.currencywizard.data.network.model.currencies.CurrencyApi
import com.veygard.currencywizard.data.network.model.currencies.toEntityList
import com.veygard.currencywizard.data.network.model.currencies.toStuffed
import com.veygard.currencywizard.domain.local.repository.LocalCurrenciesRepository
import com.veygard.currencywizard.domain.model.Currency
import com.veygard.currencywizard.domain.network.response.CurrenciesConvertRepoResponse
import com.veygard.currencywizard.domain.network.response.CurrenciesFetchRepoResponse
import com.veygard.currencywizard.domain.network.response.CurrenciesGetAllRepoResponse
import com.veygard.currencywizard.domain.network.response.CurrenciesRepoResponse
import com.veygard.currencywizard.util.extentions.round

class CurrenciesRepositoryImpl(
    private val currenciesFetchApi: CurrenciesFetchApi,
    private val currenciesGetAllApi: CurrenciesGetAllApi,
    private val currenciesConvertApi: CurrenciesConvertApi,
    private val localDbRepository: LocalCurrenciesRepository,
    private val context: Context
) : CurrenciesRepository {

    override suspend fun fetchAll(from: String): CurrenciesRepoResponse {
        return try {
            val call = currenciesFetchApi.fetchAll(from)
            when {
                call.isSuccessful -> {
                    call.body()?.let {
                        val stuffedList = getCurrencies(it.results)
                        stuffedList?.let { CurrenciesFetchRepoResponse.SuccessFetch(it) }
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
                        val stuffed = getCurrencies(it.results)
                        stuffed?.let { CurrenciesFetchRepoResponse.SuccessFetch(stuffed) }
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
                        addCurrenciesToLocalDb(it.currencies, getFavoriteCurrencies())
                        localDbRepository.insertAllCurrencies(
                            it.currencies?.toEntityList() ?: throw Exception()
                        )
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

    private suspend fun getCurrencies(currencies: List<CurrencyApi>?): List<Currency>? {
        val entityList = localDbRepository.getAllCurrencies()
        if (entityList.isEmpty()) return null
        if (currencies.isNullOrEmpty()) return null
        else {
            val stuffedList = mutableListOf<Currency>()
            currencies.forEach { currency ->
                val description =
                    entityList.singleOrNull { it.abbreviation == currency.name }?.descriptionName
                val isFavorite =
                    entityList.singleOrNull { it.abbreviation == currency.name }?.isFavorite
                val flag = FlagKit.getResId(context, currency.name.substring(0, 2))
                try {
                    stuffedList.add(
                        currency.toStuffed(
                            value = currency.value.round() ?: return null,
                            isFavorite = isFavorite ?: return null,
                            flagId = flag,
                            descriptionName = description ?: return null
                        )
                    )
                } catch (e: Exception) {
                    return null
                }
            }
            return stuffedList
        }
    }

    private suspend fun getFavoriteCurrencies(): List<Currency>? {
        return localDbRepository.getFavoriteCurrencies()
    }

    private suspend fun addCurrenciesToLocalDb(
        networkList: List<CurrencyApi>?,
        favorites: List<Currency>?
    ) {
        networkList?.forEach {
            it.flagId = FlagKit.getResId(context, it.name.substring(0,2) ?: return)
        }
        favorites?.forEach { entity ->
            val currency = networkList?.singleOrNull { it.name == entity.abbreviation }
            currency?.isFavorite = entity.isFavorite
        }
        localDbRepository.insertAllCurrencies(networkList?.toEntityList() ?: return)
    }
}