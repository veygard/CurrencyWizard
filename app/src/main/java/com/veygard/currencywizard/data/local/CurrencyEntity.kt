package com.veygard.currencywizard.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.veygard.currencywizard.domain.model.Currency
import kotlinx.parcelize.Parcelize

@Entity(tableName = "currency_table")
@Parcelize
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = false) val abbreviation: String,
    var descriptionName: String,
    var isFavorite: Boolean,
    var flagId: Int
) : Parcelable

fun CurrencyEntity.toCurrency() = Currency(
    abbreviation= abbreviation,
    value = null,
    descriptionName = descriptionName,
    isFavorite = isFavorite,
    flagId = flagId,
)

fun List<CurrencyEntity>.toCurrencyList() = map{ it.toCurrency()}



