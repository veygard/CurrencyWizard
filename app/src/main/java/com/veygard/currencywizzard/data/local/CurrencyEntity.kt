package com.veygard.currencywizzard.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.veygard.currencywizzard.data.network.model.currencies.Currency
import kotlinx.parcelize.Parcelize

@Entity(tableName = "currency_table")
@Parcelize
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = false) val abbreviation: String,
    var descriptionName: String,
    var isFavorite: Boolean
) : Parcelable



