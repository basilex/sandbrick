
package com.sandbrick.sbp.mapper

import com.sandbrick.sbp.api.v1.country.dto.CountryResponse
import com.sandbrick.sbp.domain.country.Country

fun Country.toResponse(): CountryResponse = CountryResponse(
    id = id,
    name = name,
    iso2 = iso2,
    iso3 = iso3,
    code = code,
    currencyIds = currencies.map { it.id }.toSet()
)
