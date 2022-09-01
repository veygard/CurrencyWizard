package com.veygard.currencywizzard.domain.network.usecase

data class CurrenciesUseCases(
    val fetchMultiUseCase: FetchMultiUseCase,
    val fetchAllUseCase: FetchAllUseCase,
    val getAllCurrenciesUseCase: GetAllCurrenciesUseCase,
    val convertCurrencyUseCase: ConvertCurrencyUseCase
)