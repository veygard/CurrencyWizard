package com.veygard.currencywizzard.data.network.api

import com.veygard.currencywizzard.data.network.model.currencies.convert.CurrenciesConvertApiResponse
import com.veygard.currencywizzard.data.network.model.currencies.getall.GetAllApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrenciesConvertApi {
    @GET("/convert")
    suspend fun convertApi(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double
    ): Response<CurrenciesConvertApiResponse>
}