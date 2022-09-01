package com.veygard.currencywizzard.domain.usecase

import com.veygard.currencywizzard.domain.repository.CurrenciesRepository

class GetAllCurrenciesUseCase(private val currenciesRepository: CurrenciesRepository) {
    suspend fun execute() = currenciesRepository.getAllCurrencies()
}