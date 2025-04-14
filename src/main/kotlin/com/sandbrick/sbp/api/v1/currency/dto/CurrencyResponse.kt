package com.sandbrick.sbp.api.v1.currency.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Response object representing a currency")
data class CurrencyResponse(

    @Schema(
        description = "Unique identifier of the currency",
        example = "c7f0e0a1e9r0c030"
    )
    val id: String,

    @Schema(
        description = "Currency ISO code (3-letter uppercase)",
        example = "EUR"
    )
    val code: String,

    @Schema(
        description = "Full name of the currency",
        example = "Euro"
    )
    val name: String,

    @Schema(
        description = "Symbol used for currency display",
        example = "€"
    )
    val symbol: String
)
