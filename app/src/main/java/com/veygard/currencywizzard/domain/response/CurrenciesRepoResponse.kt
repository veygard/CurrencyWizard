package com.veygard.currencywizzard.domain.response

import com.veygard.currencywizzard.data.network.model.currencies.fetch.FetchApiResponse

sealed class CurrenciesRepoResponse {
    data class SuccessFetch(val fetch: FetchApiResponse): CurrenciesRepoResponse()
    object Error: CurrenciesRepoResponse()
}

sealed class CurrenciesSuccessResponse{
    data class Fetch(val result: FetchApiResponse): CurrenciesSuccessResponse()
}