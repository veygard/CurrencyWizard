package com.veygard.currencywizard.domain.network.response

import com.veygard.currencywizard.data.network.model.currencies.convert.CurrenciesConvertApiResponse
import com.veygard.currencywizard.data.network.model.currencies.getall.GetAllApiResponse
import com.veygard.currencywizard.domain.model.Currency

interface CurrenciesRepoResponse

sealed class CurrenciesFetchRepoResponse: CurrenciesRepoResponse {
    data class SuccessFetch(val fetch: List<Currency> ): CurrenciesFetchRepoResponse()
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