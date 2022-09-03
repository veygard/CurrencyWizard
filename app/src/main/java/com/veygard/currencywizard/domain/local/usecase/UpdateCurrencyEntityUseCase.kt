package com.veygard.currencywizard.domain.local.usecase

import com.veygard.currencywizard.domain.local.repository.LocalCurrenciesRepository

class UpdateCurrencyEntityUseCase(private val localCurrenciesRepository: LocalCurrenciesRepository) {
    suspend fun execute(
        abbreviation: String,
        isFavorite: Boolean,
        descriptionName: String? = null
    ) =
        localCurrenciesRepository.updateCurrencyByAbbreviation(
            abbreviation,
            isFavorite,
            descriptionName
        )
}