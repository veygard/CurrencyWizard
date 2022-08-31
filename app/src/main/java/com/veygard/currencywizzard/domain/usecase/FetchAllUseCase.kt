package com.veygard.currencywizzard.domain.usecase

import com.veygard.currencywizzard.domain.repository.CurrenciesRepository

class FetchAllUseCase(private val currenciesRepository: CurrenciesRepository) {
    suspend fun execute(from:String) = currenciesRepository.fetchAll(from)
}