package com.veygard.currencywizard.di

import android.app.Application
import androidx.room.Room
import com.veygard.currencywizard.data.local.CurrenciesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        callback: CurrenciesDatabase.Callback
    ) = Room.databaseBuilder(app, CurrenciesDatabase::class.java, "currencies_database")
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()

    @Provides
    @Singleton
    fun provideCurrenciesDao(currenciesDatabase: CurrenciesDatabase) = currenciesDatabase.dao()



}