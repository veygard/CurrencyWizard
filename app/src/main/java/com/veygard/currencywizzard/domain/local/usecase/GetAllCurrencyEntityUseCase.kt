package com.veygard.currencywizzard.domain.local.usecase

import com.veygard.currencywizzard.domain.local.repository.LocalCurrenciesRepository

class GetAllCurrencyEntityUseCase(private val localCurrenciesRepository: LocalCurrenciesRepository) {
    suspend fun execute() = localCurrenciesRepository.getAllCurrencies()
}