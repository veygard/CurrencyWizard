package com.veygard.currencywizzard.domain.response

import com.veygard.currencywizzard.data.network.model.currencies.convert.CurrenciesConvertApiResponse
import com.veygard.currencywizzard.data.network.model.currencies.fetch.FetchApiResponse
import com.veygard.currencywizzard.data.network.model.currencies.getall.GetAllApiResponse
import java.util.*

interface CurrenciesRepoResponse

sealed class CurrenciesFetchRepoResponse: CurrenciesRepoResponse {
    data class SuccessFetch(val fetch: FetchApiResponse ): CurrenciesFetchRepoResponse()
    object Error: CurrenciesFetchRepoResponse()
}

sealed class CurrenciesGetAllRepoResponse:  CurrenciesRepoResponse {
    data class SuccessGetAll(val getAll: GetAllApiResponse): CurrenciesGetAllRepoResponse()
    object Error: CurrenciesGetAllRepoResponse()
}

sealed class CurrenciesConvertRepoResponse:  CurrenciesRepoResponse {
    data class SuccessConvert(val convert: CurrenciesConvertApiResponse): CurrenciesConvertRepoResponse()
    object Error: CurrenciesConvertRepoResponse()
}