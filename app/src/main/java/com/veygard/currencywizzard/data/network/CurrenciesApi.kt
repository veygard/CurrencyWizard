package com.veygard.currencywizzard.data.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface CurrenciesApi {
    @GET("test/catalog.spr")
    suspend fun getTest(): Response<String>
}