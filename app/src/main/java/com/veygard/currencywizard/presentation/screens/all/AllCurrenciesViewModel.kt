package com.veygard.currencywizard.presentation.screens.all

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veygard.currencywizard.di.SHARED_PREFERENCES_CURRENCY
import com.veygard.currencywizard.di.SHARED_PREFERENCES_DEFAULT_CURRENCY
import com.veygard.currencywizard.domain.local.repository.LocalCurrenciesRepository
import com.veygard.currencywizard.domain.model.Currency
import com.veygard.currencywizard.domain.network.response.CurrenciesFetchRepoResponse
import com.veygard.currencywizard.domain.network.usecase.CurrenciesUseCases
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

    private val _pickedCurrency = MutableStateFlow<String?>(null)
    val pickedCurrency: StateFlow<String?> = _pickedCurrency

    private val _totalList = MutableStateFlow<List<Currency>?>(null)
    val totalList: StateFlow<List<Currency>?> = _totalList

    init {
        getLocalCurrenciesList()
    }

    private fun getLocalCurrenciesList() {
        viewModelScope.launch {
            val result = localCurrenciesRepository.getAllCurrencies()
            if (result.isEmpty()) _stateFlow.update { AllCurrenciesState.NoLocalDb }
            else {
                _totalList.update { result }
                fetchAll()
            }
        }
    }

    fun fetchAll(currency: String? = null) {
        viewModelScope.launch {
            _stateFlow.update { AllCurrenciesState.Loading }

            val fromCurrency = currency?.let {
                savePickedCurrency(it)
                it
            } ?: run{
                loadPickedCurrency()
                _pickedCurrency.value
            }

            val result = currenciesUseCases.fetchAllUseCase.execute(
                fromCurrency ?: SHARED_PREFERENCES_DEFAULT_CURRENCY
            )
            when (result) {
                is CurrenciesFetchRepoResponse.SuccessFetch -> {
                    _stateFlow.update { AllCurrenciesState.CurrencyListReady(result.fetch) }
                }
                else -> _stateFlow.update { AllCurrenciesState.ConnectionError }
            }
        }
    }

    fun changeFavoriteState(currency: String, isFavorite: Boolean) {
        viewModelScope.launch {
            localCurrenciesRepository.updateCurrencyByAbbreviation(currency, isFavorite)
        }
    }

//    fun fetchMulti(from: String, currencyList: List<String>) {
//        viewModelScope.launch {
//            val to = currencyList.joinToString(separator = ",")
//            val result = currenciesUseCases.fetchMultiUseCase.execute(from, to)
//            when (result) {
//                is CurrenciesFetchRepoResponse.SuccessFetch -> {
//                }
//                else -> {}
//            }
//        }
//    }
//
//
//    fun convert(from: String, to: String, amount: Double) {
//        viewModelScope.launch {
//            val result = currenciesUseCases.convertCurrencyUseCase.execute(from, to, amount)
//            when (result) {
//                is CurrenciesConvertRepoResponse.SuccessConvert -> {
//                }
//            }
//        }
//    }

    fun savePickedCurrency(currency: String) {
        sharedPreferences.edit().putString(SHARED_PREFERENCES_CURRENCY, currency).apply()
    }

    fun loadPickedCurrency() {
        _pickedCurrency.update {
            sharedPreferences.getString(
                SHARED_PREFERENCES_CURRENCY,
                SHARED_PREFERENCES_DEFAULT_CURRENCY
            )
        }
    }
}