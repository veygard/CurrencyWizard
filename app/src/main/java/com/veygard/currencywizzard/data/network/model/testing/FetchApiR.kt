package com.veygard.currencywizzard.data.network.model.testing

data class FetchApiR(
    val base: String,
    val ms: Int,
    val results: Results,
    val updated: String
)

data class FetchApiMap(
    val base: String,
    val ms: Int,
    val results: Map<String,String>,
    val updated: String
)