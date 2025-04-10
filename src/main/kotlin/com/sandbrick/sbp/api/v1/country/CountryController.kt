
package com.sandbrick.sbp.api.v1.country

import com.sandbrick.sbp.api.v1.country.dto.CountryRequest
import com.sandbrick.sbp.service.CountryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/countries")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Country", description = "Country management operations")
class CountryController(
    private val countryService: CountryService
) {
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new country")
    fun create(@RequestBody @Valid request: CountryRequest) =
        countryService.create(request)

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update an existing country")
    fun update(@PathVariable id: String, @RequestBody @Valid request: CountryRequest) =
        countryService.update(id, request)

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a country by ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: String) =
        countryService.delete(id)

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all countries")
    fun getAll() = countryService.getAll()
}
