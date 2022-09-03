package com.veygard.currencywizard.domain.network.usecase

import com.veygard.currencywizard.domain.network.repository.CurrenciesRepository

class GetAllCurrenciesUseCase(private val currenciesRepository: CurrenciesRepository) {
    suspend fun execute() = currenciesRepository.getAllCurrencies()
}