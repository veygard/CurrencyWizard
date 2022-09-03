package com.veygard.currencywizard.data.network.api

import com.veygard.currencywizard.data.network.model.currencies.fetch.FetchApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrenciesFetchApi {
    @GET("/fetch-all")
    suspend fun fetchAll(@Query("from") from: String): Response<FetchApiResponse>

    @GET("/fetch-multi")
    suspend fun fetchMulti(@Query("from") from: String, @Query("to") to: String): Response<FetchApiResponse>
}