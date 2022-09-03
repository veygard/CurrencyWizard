package com.veygard.currencywizard.domain.network.usecase

import com.veygard.currencywizard.domain.network.repository.CurrenciesRepository

class FetchMultiUseCase(private val currenciesRepository: CurrenciesRepository) {
    suspend fun execute(from:String, to: String)  = currenciesRepository.fetchMulti(from = from, to= to)
}