package com.veygard.currencywizard.domain.local.usecase

import com.veygard.currencywizard.domain.local.repository.LocalCurrenciesRepository

class GetAllCurrencyEntityUseCase(private val localCurrenciesRepository: LocalCurrenciesRepository) {
    suspend fun execute() = localCurrenciesRepository.getAllCurrencies()
}