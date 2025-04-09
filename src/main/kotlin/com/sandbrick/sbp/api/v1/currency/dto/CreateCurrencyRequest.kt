package com.sandbrick.sbp.api.v1.currency.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateCurrencyRequest(
    @field:NotBlank(message = "Code must not be blank")
    @field:Size(min = 3, max = 3, message = "Code must be exactly 3 characters long")
    val code: String,

    @field:NotBlank(message = "Name must not be blank")
    val name: String,

    @field:NotBlank(message = "Symbol must not be blank")
    @field:Size(min = 1, max = 3, message = "Symbol must be between 1 and 3 characters long")
    val symbol: String
)
