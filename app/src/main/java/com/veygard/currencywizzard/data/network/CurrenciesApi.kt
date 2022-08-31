package com.veygard.currencywizzard.data.network

import com.veygard.currencywizzard.data.network.model.FetchApiResponse
import com.veygard.currencywizzard.data.network.model.testing.FetchApiR
import com.veygard.currencywizzard.data.network.model.testing2.RespFl
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrenciesApi {
    @GET("/fetch-all")
    suspend fun fetchAll(@Query("from") from: String): Response<FetchApiResponse>

    @GET("/fetch-multi")
    suspend fun fetchMulti(@Query("from") from: String, @Query("to") to: String): Response<RespFl>
}