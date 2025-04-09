
package com.sandbrick.sbp.api.v1.country.dto

data class CountryResponse(
    val id: String,
    val name: String,
    val iso2: String,
    val iso3: String,
    val code: String,
    val currencyIds: Set<String>
)
