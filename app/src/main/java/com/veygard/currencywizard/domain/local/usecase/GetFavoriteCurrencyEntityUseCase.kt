package com.veygard.currencywizard.domain.local.usecase

import com.veygard.currencywizard.domain.local.repository.LocalCurrenciesRepository

class GetFavoriteCurrencyEntityUseCase(private val localCurrenciesRepository: LocalCurrenciesRepository) {
    suspend fun execute(isFavorite: Boolean) = localCurrenciesRepository.getFavoriteCurrencies(isFavorite)
}