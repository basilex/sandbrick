package com.sandbrick.sbp.api.v1.country.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import jakarta.validation.constraints.NotEmpty

@Schema(description = "Request object for creating or updating a country")
data class CountryRequest(
    @field:NotBlank(message = "Name must not be blank")
    @Schema(description = "Name of the country", example = "Germany")
    val name: String,

    @field:NotBlank(message = "ISO2 code must not be blank")
    @field:Size(min = 2, max = 2, message = "ISO2 code must be exactly 2 characters long")
    @Schema(description = "Two-letter ISO code", example = "DE")
    val iso2: String,

    @field:NotBlank(message = "ISO3 code must not be blank")
    @field:Size(min = 3, max = 3, message = "ISO3 code must be exactly 3 characters long")
    @Schema(description = "Three-letter ISO code", example = "DEU")
    val iso3: String,

    @Schema(description = "Custom code used internally or for display", example = "DE001")
    @field:NotBlank(message = "Code must not be blank")
    val code: String,

    @field:NotEmpty(message = "Currency IDs must not be empty")
    val currencyIds: Set<String> = emptySet()
)
