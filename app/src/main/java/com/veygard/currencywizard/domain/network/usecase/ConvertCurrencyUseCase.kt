package com.veygard.currencywizard.domain.network.usecase

import com.veygard.currencywizard.domain.network.repository.CurrenciesRepository

class ConvertCurrencyUseCase(private val currenciesRepository: CurrenciesRepository) {
    suspend fun execute(from: String, to: String, amount: Double) =
        currenciesRepository.convertCurrency(from, to, amount)
}