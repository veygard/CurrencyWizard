package com.veygard.currencywizzard.domain.network.usecase

import com.veygard.currencywizzard.domain.network.repository.CurrenciesRepository

class FetchAllUseCase(private val currenciesRepository: CurrenciesRepository) {
    suspend fun execute(from:String) = currenciesRepository.fetchAll(from)
}