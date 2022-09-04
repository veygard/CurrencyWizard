package com.veygard.currencywizard.presentation.screens.convert

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.veygard.currencywizard.di.SHARED_PREFERENCES_CURRENCY
import com.veygard.currencywizard.di.SHARED_PREFERENCES_CURRENCY_CONVERT
import com.veygard.currencywizard.di.SHARED_PREFERENCES_DEFAULT_CURRENCY
import com.veygard.currencywizard.domain.local.repository.LocalCurrenciesRepository
import com.veygard.currencywizard.domain.model.Currency
import com.veygard.currencywizard.domain.network.response.CurrenciesConvertRepoResponse
import com.veygard.currencywizard.domain.network.usecase.CurrenciesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConvertCurrenciesViewModel @Inject constructor(
    private val currenciesUseCases: CurrenciesUseCases,
    private val localCurrenciesRepository: LocalCurrenciesRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<ConvertCurrenciesState?>(null)
    val stateFlow: StateFlow<ConvertCurrenciesState?> = _stateFlow

    private val _pickedCurrency = MutableStateFlow<String?>(null)
    val pickedCurrency: StateFlow<String?> = _pickedCurrency

    private val _convertToCurrency = MutableStateFlow<String?>(null)
    val convertToCurrency: StateFlow<String?> = _convertToCurrency

    private val _totalList = MutableStateFlow<List<Currency>?>(null)
    val totalList: StateFlow<List<Currency>?> = _totalList


    init {
        getLocalCurrenciesList()
        _pickedCurrency.update { loadPickedCurrency() }
        _convertToCurrency.update { loadConvertCurrency() }
    }


    private fun getLocalCurrenciesList() {
        viewModelScope.launch {
            val result = localCurrenciesRepository.getAllCurrencies()
            if (result.isEmpty()) _stateFlow.update { ConvertCurrenciesState.EmptyLocalDb }
            else {
                _stateFlow.update { ConvertCurrenciesState.BeforeConvert }
                _totalList.update { result }
            }
        }
    }

    fun convert(amount: Double) {
        viewModelScope.launch {
            val result = currenciesUseCases.convertCurrencyUseCase.execute(
                from = pickedCurrency.value ?: return@launch,
                to = convertToCurrency.value ?: return@launch,
                amount = amount
            )
            when (result) {
                is CurrenciesConvertRepoResponse.SuccessConvert -> {
                    _stateFlow.update {
                        ConvertCurrenciesState.ConvertingComplete(
                            exchangeValue = result.convert.result.value,
                            amount= result.convert.amount,
                            rate = result.convert.result.rate,
                            from = result.convert.base,
                            to = result.convert.result.currencyTo
                        )
                    }
                }
                else -> _stateFlow.update { ConvertCurrenciesState.ConnectionError }
            }
        }
    }

    fun updatePickedCurrency(currency: String) {
        storePickedCurrency(currency)
        _pickedCurrency.update { currency }
    }

    fun updateConvertToCurrency(currency: String) {
        storeConvertCurrency(currency)
        _convertToCurrency.update { currency }
    }

    fun clearConvert(){
        _stateFlow.update { ConvertCurrenciesState.BeforeConvert }
    }

    private fun storePickedCurrency(currency: String) {
        sharedPreferences.edit().putString(SHARED_PREFERENCES_CURRENCY, currency).apply()
    }

    private fun storeConvertCurrency(currency: String) {
        sharedPreferences.edit().putString(SHARED_PREFERENCES_CURRENCY_CONVERT, currency).apply()
    }

    private fun loadPickedCurrency() = sharedPreferences.getString(
        SHARED_PREFERENCES_CURRENCY,
        SHARED_PREFERENCES_DEFAULT_CURRENCY
    )

    private fun loadConvertCurrency() = sharedPreferences.getString(
        SHARED_PREFERENCES_CURRENCY_CONVERT,
        SHARED_PREFERENCES_DEFAULT_CURRENCY
    )
}