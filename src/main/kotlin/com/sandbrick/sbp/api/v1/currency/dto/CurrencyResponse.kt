package com.sandbrick.sbp.api.v1.currency.dto

data class CurrencyResponse(
    val id: String,
    val code: String,
    val name: String,
    val symbol: String
)
