package com.veygard.currencywizard.data.network.model.currencies.getall

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.veygard.currencywizard.data.network.model.currencies.CurrencyApi
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
            CurrencyApi(code, value)
        }
        val ms = context.deserialize<Int?>(obj.get("ms"), Int::class.java)

        return GetAllApiResponse(currencies = resultList, ms = ms)
    }
}