package com.veygard.currencywizard.domain.local.usecase

data class LocalCurrenciesUseCases(
    val updateCurrencyEntityUseCase: UpdateCurrencyEntityUseCase,
    val getAllCurrencyEntityUseCase: GetAllCurrencyEntityUseCase,
    val getFavoriteCurrencyEntityUseCase: GetFavoriteCurrencyEntityUseCase
)