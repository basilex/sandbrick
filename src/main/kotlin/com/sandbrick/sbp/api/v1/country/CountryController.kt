package com.sandbrick.sbp.api.v1.country

import com.sandbrick.sbp.api.v1.country.dto.CountryRequest
import com.sandbrick.sbp.api.v1.country.dto.CountryResponse
import com.sandbrick.sbp.mapper.CountryMapper
import com.sandbrick.sbp.service.CountryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
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
@Tag(name = "Country", description = "Operations related to managing countries")
class CountryController(
    private val countryService: CountryService,
    private val countryMapper: CountryMapper
) {

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Create a new country",
        responses = [ApiResponse(responseCode = "201", description = "Country created successfully")]
    )
    fun create(
        @RequestBody @Valid request: CountryRequest
    ): CountryResponse =
        countryMapper.toResponse(countryService.create(request))

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Update an existing country",
        responses = [
            ApiResponse(responseCode = "200", description = "Country updated successfully"),
            ApiResponse(responseCode = "404", description = "Country not found")
        ]
    )
    fun update(
        @PathVariable id: String,
        @RequestBody @Valid request: CountryRequest
    ): CountryResponse =
        countryMapper.toResponse(countryService.update(id, request))

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Delete a country by ID",
        responses = [
            ApiResponse(responseCode = "204", description = "Country deleted successfully"),
            ApiResponse(responseCode = "404", description = "Country not found")
        ]
    )
    fun delete(
        @PathVariable id: String
    ) = countryService.delete(id)

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all countries with pagination")
    fun getAllPaged(
        @Parameter(description = "Page number (zero-based index)", example = "0")
        @RequestParam(defaultValue = "0") page: Int,

        @Parameter(description = "Number of records per page", example = "10")
        @RequestParam(defaultValue = "10") size: Int
    ): Page<CountryResponse> {
        val countries = countryService.getAllPaged(page, size)
        val responses = countries.content.map(countryMapper::toResponse)
        return PageImpl(responses, countries.pageable, countries.totalElements)
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all countries without pagination (for dropdown lists)")
    fun getAll(): List<CountryResponse> =
        countryService.getAll().map(countryMapper::toResponse)
}
