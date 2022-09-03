package com.veygard.currencywizzard.presentation.screens.all

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veygard.currencywizzard.di.SHARED_PREFERENCES_CURRENCY
import com.veygard.currencywizzard.di.SHARED_PREFERENCES_DEFAULT_CURRENCY
import com.veygard.currencywizzard.domain.local.repository.LocalCurrenciesRepository
import com.veygard.currencywizzard.domain.network.response.CurrenciesConvertRepoResponse
import com.veygard.currencywizzard.domain.network.response.CurrenciesFetchRepoResponse
import com.veygard.currencywizzard.domain.network.usecase.CurrenciesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllCurrenciesViewModel @Inject constructor(
    private val currenciesUseCases: CurrenciesUseCases,
    private val localCurrenciesRepository: LocalCurrenciesRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<AllCurrenciesState?>(null)
    val stateFlow: StateFlow<AllCurrenciesState?> = _stateFlow


    init {
        getLocalCurrenciesList()
    }

    private fun getLocalCurrenciesList() {
        viewModelScope.launch {
            val result = localCurrenciesRepository.getAllCurrencies()
            if (result.isEmpty()) _stateFlow.update { AllCurrenciesState.NoLocalDb }
            else fetchAll()
        }
    }

    fun fetchAll() {
        viewModelScope.launch {
            val fromCurrency = sharedPreferences.getString(
                SHARED_PREFERENCES_CURRENCY,
                SHARED_PREFERENCES_DEFAULT_CURRENCY
            )
            val result = currenciesUseCases.fetchAllUseCase.execute(
                fromCurrency ?: SHARED_PREFERENCES_DEFAULT_CURRENCY
            )
            when (result) {
                is CurrenciesFetchRepoResponse.SuccessFetch -> {

                }
                else -> {}
            }
        }
    }

    fun fetchMulti(from: String, currencyList: List<String>) {
        viewModelScope.launch {
            val to = currencyList.joinToString(separator = ",")
            val result = currenciesUseCases.fetchMultiUseCase.execute(from, to)
            when (result) {
                is CurrenciesFetchRepoResponse.SuccessFetch -> {
                }
                else -> {}
            }
        }
    }

    fun convert(from: String, to: String, amount: Double) {
        viewModelScope.launch {
            val result = currenciesUseCases.convertCurrencyUseCase.execute(from, to, amount)
            when (result) {
                is CurrenciesConvertRepoResponse.SuccessConvert -> {
                }
            }
        }
    }
}