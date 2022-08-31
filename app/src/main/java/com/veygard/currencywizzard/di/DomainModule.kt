package com.veygard.currencywizzard.di

import com.veygard.currencywizzard.data.network.CurrenciesApi
import com.veygard.currencywizzard.domain.repository.CurrenciesRepository
import com.veygard.currencywizzard.domain.repository.CurrenciesRepositoryImpl
import com.veygard.currencywizzard.domain.usecase.CurrenciesUseCases
import com.veygard.currencywizzard.domain.usecase.FetchAllUseCase
import com.veygard.currencywizzard.domain.usecase.FetchMultiUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Singleton

@DelicateCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    @Singleton
    fun provideStockRepository(
        currenciesApi: CurrenciesApi
    ): CurrenciesRepository = CurrenciesRepositoryImpl(currenciesApi)

    @Provides
    @Singleton
    fun provideCurrenciesUseCases(
        currenciesRepository: CurrenciesRepository,
    ): CurrenciesUseCases = CurrenciesUseCases(
        fetchMultiUseCase = FetchMultiUseCase(currenciesRepository),
        fetchAllUseCase = FetchAllUseCase(currenciesRepository)
    )
}