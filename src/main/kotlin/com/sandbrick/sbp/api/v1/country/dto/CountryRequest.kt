package com.sandbrick.sbp.api.v1.country.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

@Schema(description = "Payload for creating or updating a country")
data class CountryRequest(

    @field:NotBlank(message = "Name must not be blank")
    @Schema(description = "Full name of the country", example = "Germany", required = true)
    val name: String,

    @field:NotBlank(message = "ISO2 code must not be blank")
    @field:Size(min = 2, max = 2, message = "ISO2 code must be exactly 2 characters long")
    @Schema(description = "Two-letter ISO 3166-1 alpha-2 code", example = "DE", required = true)
    val iso2: String,

    @field:NotBlank(message = "ISO3 code must not be blank")
    @field:Size(min = 3, max = 3, message = "ISO3 code must be exactly 3 characters long")
    @Schema(description = "Three-letter ISO 3166-1 alpha-3 code", example = "DEU", required = true)
    val iso3: String,

    @field:NotBlank(message = "Code must not be blank")
    @Schema(description = "Internal custom code (e.g. used for UI display)", example = "DE001", required = true)
    val code: String,

    @field:NotEmpty(message = "At least one currency ID is required")
    @Schema(
        description = "Set of associated currency IDs for the country",
        example = "[\"usd\", \"eur\"]",
        required = true
    )
    val currencyIds: Set<String> = emptySet()
)
