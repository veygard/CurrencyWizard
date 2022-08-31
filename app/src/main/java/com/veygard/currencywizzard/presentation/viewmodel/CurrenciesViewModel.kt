package com.veygard.currencywizzard.presentation.viewmodel

import android.os.AsyncTask.execute
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veygard.currencywizzard.data.network.model.Currency
import com.veygard.currencywizzard.domain.response.CurrenciesRepoResponse
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
            val result  = currenciesUseCases.fetchMultiUseCase.execute("RUB", "EUR,Gbp")
            when(result){
                is CurrenciesRepoResponse.SuccessFetch -> {
                    _stateFlow.value = emptyList()
                }
                else -> {}
            }
        }
    }
}