package com.veygard.currencywizard.presentation.screens.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veygard.currencywizard.domain.network.response.CurrenciesGetAllRepoResponse
import com.veygard.currencywizard.domain.network.usecase.CurrenciesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor(
    private val currenciesUseCases: CurrenciesUseCases,
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<StartScreenState?>(StartScreenState.Loading)
    val stateFlow: StateFlow<StartScreenState?> = _stateFlow

    fun getAllCurrencies() {
        viewModelScope.launch {
            _stateFlow.update { StartScreenState.Loading }
            val result = currenciesUseCases.getAllCurrenciesUseCase.execute()
            delay(1500) //demonstration purpose
            when (result) {
                is CurrenciesGetAllRepoResponse.SuccessGetAll -> {
                    _stateFlow.update {
                        result.getAll.currencies?.let { _ ->
                            StartScreenState.Success
                        } ?: StartScreenState.ConnectionError
                    }
                }
                CurrenciesGetAllRepoResponse.Error -> _stateFlow.update { StartScreenState.ConnectionError }
            }
        }
    }
}