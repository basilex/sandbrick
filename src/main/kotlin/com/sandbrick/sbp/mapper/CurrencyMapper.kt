package com.sandbrick.sbp.mapper

import com.sandbrick.sbp.api.v1.currency.dto.CurrencyResponse
import com.sandbrick.sbp.domain.currency.Currency

fun Currency.toResponse(): CurrencyResponse = CurrencyResponse(
    id = id.toString(),
    code = code,
    name = name,
    symbol = symbol
)
