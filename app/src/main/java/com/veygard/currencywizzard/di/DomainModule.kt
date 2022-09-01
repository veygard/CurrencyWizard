package com.veygard.currencywizzard.di

import com.veygard.currencywizzard.data.local.CurrenciesDao
import com.veygard.currencywizzard.data.network.api.CurrenciesConvertApi
import com.veygard.currencywizzard.data.network.api.CurrenciesFetchApi
import com.veygard.currencywizzard.data.network.api.CurrenciesGetAllApi
import com.veygard.currencywizzard.domain.local.repository.LocalCurrenciesRepository
import com.veygard.currencywizzard.domain.local.repository.LocalCurrenciesRepositoryImpl
import com.veygard.currencywizzard.domain.local.usecase.GetAllCurrencyEntityUseCase
import com.veygard.currencywizzard.domain.local.usecase.GetFavoriteCurrencyEntityUseCase
import com.veygard.currencywizzard.domain.local.usecase.LocalCurrenciesUseCases
import com.veygard.currencywizzard.domain.local.usecase.UpdateCurrencyEntityUseCase
import com.veygard.currencywizzard.domain.network.repository.CurrenciesRepository
import com.veygard.currencywizzard.domain.network.repository.CurrenciesRepositoryImpl
import com.veygard.currencywizzard.domain.network.usecase.*
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
        currenciesConvertApi: CurrenciesConvertApi,
        localCurrenciesRepository: LocalCurrenciesRepository
    ): CurrenciesRepository = CurrenciesRepositoryImpl(
        currenciesFetchApi,
        currenciesGetAllApi,
        currenciesConvertApi,
        localCurrenciesRepository
    )

    @Provides
    @Singleton
    fun provideCurrenciesUseCases(
        currenciesRepository: CurrenciesRepository,
    ): CurrenciesUseCases = CurrenciesUseCases(
        fetchMultiUseCase = FetchMultiUseCase(currenciesRepository),
        fetchAllUseCase = FetchAllUseCase(currenciesRepository),
        convertCurrencyUseCase = ConvertCurrencyUseCase((currenciesRepository)),
        getAllCurrenciesUseCase = GetAllCurrenciesUseCase(currenciesRepository)
    )

    @Provides
    @Singleton
    fun provideLocalCurrencyUseCases(
        localCurrenciesRepository: LocalCurrenciesRepository
    ): LocalCurrenciesUseCases = LocalCurrenciesUseCases(
        updateCurrencyEntityUseCase = UpdateCurrencyEntityUseCase(localCurrenciesRepository),
        getAllCurrencyEntityUseCase = GetAllCurrencyEntityUseCase(localCurrenciesRepository),
        getFavoriteCurrencyEntityUseCase = GetFavoriteCurrencyEntityUseCase(localCurrenciesRepository)
    )

    @Provides
    @Singleton
    fun provideLocalDbRepository(
        currenciesDao: CurrenciesDao
    ): LocalCurrenciesRepository = LocalCurrenciesRepositoryImpl(currenciesDao)
}