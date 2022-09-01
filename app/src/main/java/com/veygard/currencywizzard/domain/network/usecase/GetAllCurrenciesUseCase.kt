package com.veygard.currencywizzard.domain.network.usecase

import com.veygard.currencywizzard.domain.network.repository.CurrenciesRepository

class GetAllCurrenciesUseCase(private val currenciesRepository: CurrenciesRepository) {
    suspend fun execute() = currenciesRepository.getAllCurrencies()
}