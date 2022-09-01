package com.veygard.currencywizzard.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veygard.currencywizzard.domain.local.repository.LocalCurrenciesRepository
import com.veygard.currencywizzard.domain.network.response.CurrenciesConvertRepoResponse
import com.veygard.currencywizzard.domain.network.response.CurrenciesFetchRepoResponse
import com.veygard.currencywizzard.domain.network.response.CurrenciesGetAllRepoResponse
import com.veygard.currencywizzard.domain.network.usecase.CurrenciesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val currenciesUseCases: CurrenciesUseCases,
    private val localCurrenciesRepository: LocalCurrenciesRepository
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<MainScreenState?>(null)
    val stateFlow: StateFlow<MainScreenState?> = _stateFlow

    init {
        getAllCurrencies()
    }

    fun fetchAll(from: String = "USD") {
        viewModelScope.launch {
            val result = currenciesUseCases.fetchAllUseCase.execute(from)
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

    fun getAllCurrencies() {
        viewModelScope.launch {
            _stateFlow.update { MainScreenState.Loading }
            val result = currenciesUseCases.getAllCurrenciesUseCase.execute()
            when (result) {
                is CurrenciesGetAllRepoResponse.SuccessGetAll -> {
                    _stateFlow.update {
                        result.getAll.currencies?.let { list ->
                            MainScreenState.CurrencyListReady(list)
                        } ?: MainScreenState.ListError
                    }
                }
                CurrenciesGetAllRepoResponse.Error -> _stateFlow.update { MainScreenState.ConnectionError }
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