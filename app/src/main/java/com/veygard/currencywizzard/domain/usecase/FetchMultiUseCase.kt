package com.veygard.currencywizzard.domain.usecase

import com.veygard.currencywizzard.domain.repository.CurrenciesRepository

class FetchMultiUseCase(private val currenciesRepository: CurrenciesRepository) {
    suspend fun execute(from:String, to: String)  = currenciesRepository.fetchMulti(from = from, to= to)
}