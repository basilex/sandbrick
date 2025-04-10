package com.sandbrick.sbp.api.v1.currency.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "Request object for creating or updating a currency")
data class CurrencyRequest(
    @field:NotBlank(message = "Code must not be blank")
    @field:Size(min = 3, max = 3, message = "Code must be exactly 3 characters long")
    @Schema(description = "Currency ISO code (3 letters)", example = "EUR")
    val code: String,

    @field:NotBlank(message = "Name must not be blank")
    @Schema(description = "Currency name", example = "Euro")
    val name: String,

    @field:NotBlank(message = "Symbol must not be blank")
    @field:Size(min = 1, max = 3, message = "Symbol must be between 1 and 3 characters long")
    @Schema(description = "Currency symbol", example = "€")
    val symbol: String
)
