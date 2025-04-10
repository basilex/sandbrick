package com.sandbrick.sbp.service

import com.sandbrick.sbp.api.v1.country.dto.CountryResponse
import com.sandbrick.sbp.api.v1.country.dto.CountryRequest
import com.sandbrick.sbp.domain.Country
import com.sandbrick.sbp.exception.ResourceNotFoundException
import com.sandbrick.sbp.repository.CountryRepository
import com.sandbrick.sbp.repository.CurrencyRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CountryService(
    private val countryRepository: CountryRepository,
    private val currencyRepository: CurrencyRepository
) {

    @Transactional
    fun create(request: CountryRequest): CountryResponse {
        val currencies = currencyRepository.findAllById(request.currencyIds)
        val country = Country(
            name = request.name,
            iso2 = request.iso2,
            iso3 = request.iso3,
            code = request.code,
            currencies = currencies.toMutableSet()
        )
        return countryRepository.save(country).toResponse()
    }

    @Transactional
    fun update(id: String, request: CountryRequest): CountryResponse {
        val country = countryRepository.findById(id).orElseThrow {
            ResourceNotFoundException("Country with id $id not found")
        }
        val currencies = currencyRepository.findAllById(request.currencyIds)

        country.name = request.name
        country.iso2 = request.iso2
        country.iso3 = request.iso3
        country.code = request.code
        country.currencies.clear()
        country.currencies.addAll(currencies)

        return countryRepository.save(country).toResponse()
    }

    @Transactional
    fun delete(id: String) = countryRepository.deleteById(id)

    fun getAll(): List<CountryResponse> = countryRepository.findAll().map { it.toResponse() }

    private fun Country.toResponse(): CountryResponse = CountryResponse(
        id = id,
        name = name,
        iso2 = iso2,
        iso3 = iso3,
        code = code,
        currencyIds = currencies.map { it.id }.toSet()
    )

}
