package com.veygard.currencywizard.domain.network.usecase

import com.veygard.currencywizard.domain.network.repository.CurrenciesRepository

class FetchAllUseCase(private val currenciesRepository: CurrenciesRepository) {
    suspend fun execute(from:String) = currenciesRepository.fetchAll(from)
}