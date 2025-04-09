package com.sandbrick.sbp.api.v1.country.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import jakarta.validation.constraints.NotEmpty

data class CreateCountryRequest(
    @field:NotBlank(message = "Name must not be blank")
    val name: String,

    @field:NotBlank(message = "ISO2 code must not be blank")
    @field:Size(min = 2, max = 2, message = "ISO2 code must be exactly 2 characters long")
    val iso2: String,

    @field:NotBlank(message = "ISO3 code must not be blank")
    @field:Size(min = 3, max = 3, message = "ISO3 code must be exactly 3 characters long")
    val iso3: String,

    @field:NotBlank(message = "Code must not be blank")
    val code: String,

    @field:NotEmpty(message = "Currency IDs must not be empty")
    val currencyIds: Set<String> = emptySet()
)
