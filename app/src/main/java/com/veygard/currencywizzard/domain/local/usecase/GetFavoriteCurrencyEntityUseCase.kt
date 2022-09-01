package com.veygard.currencywizzard.domain.local.usecase

import com.veygard.currencywizzard.domain.local.repository.LocalCurrenciesRepository

class GetFavoriteCurrencyEntityUseCase(private val localCurrenciesRepository: LocalCurrenciesRepository) {
    suspend fun execute(isFavorite: Boolean) = localCurrenciesRepository.getFavoriteCurrencies(isFavorite)
}