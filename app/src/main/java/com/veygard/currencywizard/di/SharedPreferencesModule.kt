package com.veygard.currencywizard.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val SHARED_PREFERENCES_NAME = "wizPref"
const val SHARED_PREFERENCES_CURRENCY = "wizPrefCurrency"
const val SHARED_PREFERENCES_CURRENCY_CONVERT = "wizConvertPrefCurrency"
const val SHARED_PREFERENCES_DEFAULT_CURRENCY = "USD"
@Module
@InstallIn(SingletonComponent::class)
class SharedPreferencesModule {
    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
}