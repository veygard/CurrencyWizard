package com.veygard.currencywizzard.data.network.api

import retrofit2.http.GET

interface CurrenciesGetAllApi {
    @GET("/currencies")
    suspend fun getAll(): Unit
}