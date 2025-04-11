package com.sandbrick.sbp.api.v1.country

import com.sandbrick.sbp.api.v1.country.dto.CountryRequest
import com.sandbrick.sbp.api.v1.country.dto.CountryResponse
import com.sandbrick.sbp.mapper.CountryMapper
import com.sandbrick.sbp.service.CountryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/countries")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Country", description = "Country management operations")
class CountryController(
    private val countryService: CountryService,
    private val countryMapper: CountryMapper
) {

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new country")
    fun create(@RequestBody @Valid request: CountryRequest): CountryResponse {
        val country = countryService.create(request)
        return countryMapper.toResponse(country)
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update an existing country")
    fun update(@PathVariable id: String, @RequestBody @Valid request: CountryRequest): CountryResponse {
        val country = countryService.update(id, request)
        return countryMapper.toResponse(country)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a country by ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: String) = countryService.delete(id)

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all countries with pagination")
    fun getAllPaged(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Page<CountryResponse> {
        val countries = countryService.getAllPaged(page, size)
        val responses = countries.content.map { countryMapper.toResponse(it) }
        return PageImpl(responses, countries.pageable, countries.totalElements)
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all countries without pagination (for dropdown lists)")
    fun getAll(): List<CountryResponse> {
        val countries = countryService.getAll()
        return countries.map { countryMapper.toResponse(it) }
    }
}
