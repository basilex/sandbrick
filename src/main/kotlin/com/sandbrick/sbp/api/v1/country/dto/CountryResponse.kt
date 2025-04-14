package com.sandbrick.sbp.api.v1.country.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Represents country information returned by the API")
data class CountryResponse(

    @Schema(description = "Unique country identifier", example = "fc7f0e0a1e9r0c028")
    val id: String,

    @Schema(description = "Full name of the country", example = "Germany")
    val name: String,

    @Schema(description = "Two-letter ISO 3166-1 alpha-2 code", example = "DE")
    val iso2: String,

    @Schema(description = "Three-letter ISO 3166-1 alpha-3 code", example = "DEU")
    val iso3: String,

    @Schema(description = "Internal custom country code (used for display or logic)", example = "DE001")
    val code: String,

    @Schema(
        description = "Set of associated currency IDs (linked to the country)",
        example = "[\"USD\", \"EUR\"]"
    )
    val currencyIds: Set<String>
)
