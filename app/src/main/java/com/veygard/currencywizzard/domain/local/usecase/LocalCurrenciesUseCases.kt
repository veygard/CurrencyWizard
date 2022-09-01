package com.veygard.currencywizzard.domain.local.usecase

data class LocalCurrenciesUseCases(
    val updateCurrencyEntityUseCase: UpdateCurrencyEntityUseCase,
    val getAllCurrencyEntityUseCase: GetAllCurrencyEntityUseCase,
    val getFavoriteCurrencyEntityUseCase: GetFavoriteCurrencyEntityUseCase
)