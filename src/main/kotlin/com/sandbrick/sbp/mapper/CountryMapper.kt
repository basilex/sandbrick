package com.sandbrick.sbp.mapper

import com.sandbrick.sbp.api.v1.country.dto.CountryRequest
import com.sandbrick.sbp.api.v1.country.dto.CountryResponse
import com.sandbrick.sbp.domain.Country
import org.springframework.stereotype.Component

@Component
class CountryMapper {
    fun toResponse(country: Country): CountryResponse = CountryResponse(
        id = country.id,
        name = country.name,
        iso2 = country.iso2,
        iso3 = country.iso3,
        code = country.code,
        currencyIds = country.currencies.map { it.id }.toSet()
    )

    fun toEntity(request: CountryRequest): Country = Country(
        name = request.name,
        iso2 = request.iso2,
        iso3 = request.iso3,
        code = request.code
        // currencies filled in service
    )
}
