package com.sandbrick.sbp.api.v1.currency.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Currency response model")
data class CurrencyResponse(
    @Schema(description = "Unique identifier of the currency", example = "cfa3e3ab-37b7-413e-93f2-50a2c76597a9")
    val id: String,

    @Schema(description = "Currency code", example = "EUR")
    val code: String,

    @Schema(description = "Currency name", example = "Euro")
    val name: String,

    @Schema(description = "Currency symbol", example = "€")
    val symbol: String
)
