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
import com.veygard.currencywizard.presentation.model.SortingOrder
import com.veygard.currencywizard.presentation.model.SortingTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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

    private val _sortedBy = MutableStateFlow(SortingTypes.ABC)
    val sortedBy: StateFlow<SortingTypes> = _sortedBy
    private val _sortedOrder = MutableStateFlow(SortingOrder.ASCENDING)
    val sortedOrder: StateFlow<SortingOrder> = _sortedOrder

    private var originalList: MutableList<Currency>? = null

    init {
        getLocalCurrenciesList()
        _pickedCurrency.update { loadPickedCurrency() }
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

    fun updatePickedCurrency(currency: String) {
        storePickedCurrency(currency)
        _pickedCurrency.update { currency }
    }

    fun fetchAll() {
        viewModelScope.launch {
            _stateFlow.update { AllCurrenciesState.Loading }
            delay(500) //demonstration purpose
            val fromCurrency = loadPickedCurrency()

            val result = currenciesUseCases.fetchAllUseCase.execute(
                fromCurrency ?: SHARED_PREFERENCES_DEFAULT_CURRENCY
            )
            when (result) {
                is CurrenciesFetchRepoResponse.SuccessFetch -> {
                    _stateFlow.update { AllCurrenciesState.CurrencyListReady(result.fetch) }
                    originalList = result.fetch.toMutableList()
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

    fun sortByType(type: SortingTypes) {
        viewModelScope.launch {
            when (type) {
                SortingTypes.ABC -> {
                    _sortedBy.update { SortingTypes.ABC }
                    _stateFlow.update {
                        sortByAbc(
                            originalList
                        )?.let {
                            AllCurrenciesState.CurrencyListReady(
                                it
                            )
                        } ?: AllCurrenciesState.ListError
                    }
                }
                SortingTypes.VALUE -> {
                    _sortedBy.update { SortingTypes.VALUE }
                    _stateFlow.update {
                        sortByValue(
                            originalList
                        )?.let {
                            AllCurrenciesState.CurrencyListReady(
                                it
                            )
                        } ?: AllCurrenciesState.ListError
                    }
                }
            }
        }
    }

    fun sortByOder(order: SortingOrder) {
        viewModelScope.launch {
            when (order) {
                SortingOrder.ASCENDING -> _sortedOrder.update { SortingOrder.ASCENDING }
                SortingOrder.DESCENDING -> _sortedOrder.update { SortingOrder.DESCENDING }
            }
            sortByType(_sortedBy.value)
        }
    }

    private fun sortByAbc(list: List<Currency>?): List<Currency>? = list?.let {
        return when (sortedOrder.value) {
            SortingOrder.DESCENDING -> list.sortedByDescending { it.abbreviation }
            SortingOrder.ASCENDING -> list.sortedBy { it.abbreviation }
        }
    }

    private fun sortByValue(list: List<Currency>?): List<Currency>? = list?.let {
        return when (sortedOrder.value) {
            SortingOrder.DESCENDING -> list.sortedByDescending { it.value?.toDouble() }
            SortingOrder.ASCENDING -> list.sortedBy { it.value?.toDouble() }
        }
    }

    private fun storePickedCurrency(currency: String) {
        sharedPreferences.edit().putString(SHARED_PREFERENCES_CURRENCY, currency).apply()
    }

    private fun loadPickedCurrency() = sharedPreferences.getString(
        SHARED_PREFERENCES_CURRENCY,
        SHARED_PREFERENCES_DEFAULT_CURRENCY
    )
}