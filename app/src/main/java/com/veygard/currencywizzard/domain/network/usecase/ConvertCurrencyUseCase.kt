package com.veygard.currencywizzard.domain.network.usecase

import com.veygard.currencywizzard.domain.network.repository.CurrenciesRepository

class ConvertCurrencyUseCase(private val currenciesRepository: CurrenciesRepository) {
    suspend fun execute(from: String, to: String, amount: Double) =
        currenciesRepository.convertCurrency(from, to, amount)
}