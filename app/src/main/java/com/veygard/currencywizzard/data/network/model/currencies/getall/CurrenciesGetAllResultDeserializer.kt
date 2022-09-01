package com.veygard.currencywizzard.data.network.model.currencies.getall

import androidx.compose.ui.focus.FocusDirection.Companion.In
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.veygard.currencywizzard.data.network.model.currencies.Currency
import com.veygard.currencywizzard.data.network.model.currencies.fetch.FetchApiResponse
import java.lang.reflect.Type

class CurrenciesGetAllResultDeserializer : JsonDeserializer<GetAllApiResponse> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): GetAllApiResponse {
        if (json == null || context == null) {
            throw Exception("deserialize Error")
        }
        val obj = json.asJsonObject

        val resultSet = obj.get("currencies").asJsonObject.entrySet()
        val resultList = resultSet.map {
            val code = it.key
            val value = it.value.asString
            Currency(code, value)
        }
        val ms = context.deserialize<Int?>(obj.get("ms"), Int::class.java)

        return GetAllApiResponse(currencies = resultList, ms = ms)
    }
}