package com.veygard.currencywizzard.di

import com.veygard.currencywizzard.data.network.api.CurrenciesConvertApi
import com.veygard.currencywizzard.data.network.api.CurrenciesFetchApi
import com.veygard.currencywizzard.data.network.api.CurrenciesGetAllApi
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
    fun provideCurrenciesRepository(
        currenciesFetchApi: CurrenciesFetchApi,
        currenciesGetAllApi: CurrenciesGetAllApi,
        currenciesConvertApi: CurrenciesConvertApi
    ): CurrenciesRepository = CurrenciesRepositoryImpl(currenciesFetchApi, currenciesGetAllApi, currenciesConvertApi)

    @Provides
    @Singleton
    fun provideCurrenciesUseCases(
        currenciesRepository: CurrenciesRepository,
    ): CurrenciesUseCases = CurrenciesUseCases(
        fetchMultiUseCase = FetchMultiUseCase(currenciesRepository),
        fetchAllUseCase = FetchAllUseCase(currenciesRepository)
    )
}