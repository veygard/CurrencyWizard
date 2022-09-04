package com.veygard.currencywizard.data.network.model.currencies.fetch

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.veygard.currencywizard.data.network.model.currencies.CurrencyApi
import java.lang.reflect.Type

class FetchResultDeserializer : JsonDeserializer<FetchApiResponse> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): FetchApiResponse {
        if (json == null || context == null) {
            throw Exception("deserialize Error")
        }
        val obj = json.asJsonObject

        val base = context.deserialize<String?>(obj.get("base"), String::class.java)
        val resultSet = obj.get("results").asJsonObject.entrySet()
        val resultList = resultSet.map {
            val code = it.key
            val value = it.value.asString
            CurrencyApi(code, value)
        }
        val updated = context.deserialize<String?>(obj.get("updated"), String::class.java)
        val ms = context.deserialize<Int?>(obj.get("ms"), Int::class.java)

        return FetchApiResponse(base = base, ms = ms, results = resultList, updated = updated)
    }
}