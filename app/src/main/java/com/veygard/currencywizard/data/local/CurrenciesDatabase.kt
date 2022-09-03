package com.veygard.currencywizard.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [CurrencyEntity::class], version = 1)
abstract class CurrenciesDatabase : RoomDatabase() {

    abstract fun dao(): CurrenciesDao


    class Callback @Inject constructor(
        private val database: Provider<CurrenciesDatabase>,
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
        }
    }
}