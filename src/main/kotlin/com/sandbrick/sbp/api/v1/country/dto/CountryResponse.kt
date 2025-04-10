
package com.sandbrick.sbp.api.v1.country.dto

import io.swagger.v3.oas.annotations.media.Schema

data class CountryResponse(
    @Schema(description = "Country response model")
    val id: String,

    @Schema(description = "Country name", example = "Germany")
    val name: String,

    @Schema(description = "ISO 2-letter code", example = "DE")
    val iso2: String,

    @Schema(description = "ISO 3-letter code", example = "DEU")
    val iso3: String,

    @Schema(description = "Custom code", example = "DE001")
    val code: String,

    val currencyIds: Set<String>
)
