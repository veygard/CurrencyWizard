package com.veygard.currencywizzard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veygard.currencywizzard.data.network.model.currencies.Currency
import com.veygard.currencywizzard.domain.response.CurrenciesFetchRepoResponse
import com.veygard.currencywizzard.domain.usecase.CurrenciesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel  @Inject constructor(private val currenciesUseCases: CurrenciesUseCases) : ViewModel(){

    private val _stateFlow = MutableStateFlow<List<Currency>?>(null)
    val stateFlow = _stateFlow.asStateFlow()

    init {
        viewModelScope.launch {
//            val result  = currenciesUseCases.fetchMultiUseCase.execute("RUB", "EUR,Gbp")
            val result  = currenciesUseCases.fetchAllUseCase.execute("RUB")
            when(result){
                is CurrenciesFetchRepoResponse.SuccessFetch -> {
                    _stateFlow.value = result.fetch.results
                }
                else -> {}
            }
        }
    }
}