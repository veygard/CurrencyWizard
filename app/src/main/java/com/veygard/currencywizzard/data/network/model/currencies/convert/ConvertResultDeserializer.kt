package com.veygard.currencywizzard.data.network.model.currencies.convert

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class ConvertResultDeserializer : JsonDeserializer<CurrenciesConvertApiResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): CurrenciesConvertApiResponse {
        if (json == null || context == null) {
            throw Exception("deserialize Error")
        }
        val obj = json.asJsonObject

        val base = context.deserialize<String?>(obj.get("base"), String::class.java)
        val amount = context.deserialize<Double?>(obj.get("amount"), Double::class.java)
        val resultSet = obj.get("result").asJsonObject.entrySet()

        var currencyName = ""
        var rate = 0.0
        resultSet.forEach {
            if (it.key == "rate") rate = it.value.asDouble
            else currencyName = it.value.asString
        }
        val ms = context.deserialize<Int?>(obj.get("ms"), Int::class.java)

        return CurrenciesConvertApiResponse(
            base = base,
            ms = ms,
            result = ConvertCurrenciesResult(currencyName, rate),
            amount = amount
        )
    }
}