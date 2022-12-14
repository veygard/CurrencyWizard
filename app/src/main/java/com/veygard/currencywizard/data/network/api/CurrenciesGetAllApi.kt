package com.veygard.currencywizard.data.network.api

import com.veygard.currencywizard.data.network.model.currencies.getall.GetAllApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface CurrenciesGetAllApi {
    @GET("/currencies")
    suspend fun getAll(): Response<GetAllApiResponse>
}