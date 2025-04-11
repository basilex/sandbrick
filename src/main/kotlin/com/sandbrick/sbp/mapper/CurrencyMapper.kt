package com.sandbrick.sbp.mapper

import com.sandbrick.sbp.api.v1.currency.dto.CurrencyRequest
import com.sandbrick.sbp.api.v1.currency.dto.CurrencyResponse
import com.sandbrick.sbp.domain.Currency
import org.springframework.stereotype.Component

@Component
class CurrencyMapper {

    fun toResponse(currency: Currency): CurrencyResponse =
        CurrencyResponse(
            id = currency.id,
            name = currency.name,
            code = currency.code,
            symbol = currency.symbol
        )

    fun toEntity(request: CurrencyRequest): Currency =
        Currency(
            name = request.name,
            code = request.code,
            symbol = request.symbol
        )
}
