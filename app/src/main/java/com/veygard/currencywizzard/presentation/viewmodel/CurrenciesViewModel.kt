package com.veygard.currencywizzard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veygard.currencywizzard.data.network.model.currencies.Currency
import com.veygard.currencywizzard.domain.local.repository.LocalCurrenciesRepository
import com.veygard.currencywizzard.domain.network.response.CurrenciesConvertRepoResponse
import com.veygard.currencywizzard.domain.network.response.CurrenciesFetchRepoResponse
import com.veygard.currencywizzard.domain.network.response.CurrenciesGetAllRepoResponse
import com.veygard.currencywizzard.domain.network.usecase.CurrenciesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel  @Inject constructor(
    private val currenciesUseCases: CurrenciesUseCases,
    private val localCurrenciesRepository: LocalCurrenciesRepository
) : ViewModel(){

    private val _stateFlow = MutableStateFlow<List<Currency>?>(null)
    val stateFlow = _stateFlow.asStateFlow()

    init {
        fetchAll()
        fetchMulti("usd", listOf("eur","gbp"))
        getAllCurrencies()
        convert(to="rub", from = "usd", amount = 1.65)
    }

    fun fetchAll(from: String = "USD"){
        viewModelScope.launch {
            val result  = currenciesUseCases.fetchAllUseCase.execute(from)
            when(result){
                is CurrenciesFetchRepoResponse.SuccessFetch -> {
                    _stateFlow.value = result.fetch.results
                }
                else -> {}
            }
        }
    }

    fun fetchMulti(from:String, currencyList: List<String>){
        viewModelScope.launch {
            val to = currencyList.joinToString(separator = ",")
            val result  = currenciesUseCases.fetchMultiUseCase.execute(from,to)
            when(result){
                is CurrenciesFetchRepoResponse.SuccessFetch -> {
                    _stateFlow.value = result.fetch.results
                }
                else -> {}
            }
        }
    }

    fun getAllCurrencies(){
        viewModelScope.launch {
            val result  = currenciesUseCases.getAllCurrenciesUseCase.execute()
            when(result){
                is CurrenciesGetAllRepoResponse.SuccessGetAll  -> {
                }
                else  -> {}
            }
        }
    }

    fun convert(from: String, to: String, amount: Double) {
        viewModelScope.launch {
            val result = currenciesUseCases.convertCurrencyUseCase.execute(from, to, amount)
            when(result){
                is CurrenciesConvertRepoResponse.SuccessConvert ->{
                }
            }
        }
    }
}