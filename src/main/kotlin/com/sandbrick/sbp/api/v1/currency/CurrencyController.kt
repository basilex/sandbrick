package com.sandbrick.sbp.api.v1.currency

import com.sandbrick.sbp.api.v1.currency.dto.CurrencyRequest
import com.sandbrick.sbp.api.v1.currency.dto.CurrencyResponse
import com.sandbrick.sbp.mapper.CurrencyMapper
import com.sandbrick.sbp.service.CurrencyService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.PageImpl
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/currencies")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Currency", description = "Currency management operations")
class CurrencyController(
    private val currencyService: CurrencyService,
    private val currencyMapper: CurrencyMapper
) {

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Create a new currency",
        responses = [
            ApiResponse(responseCode = "201", description = "Currency created successfully"),
            ApiResponse(responseCode = "400", description = "Invalid input")
        ]
    )
    fun create(
        @RequestBody @Valid request: CurrencyRequest
    ): CurrencyResponse =
        currencyService.create(request).let(currencyMapper::toResponse)

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Update an existing currency",
        responses = [
            ApiResponse(responseCode = "200", description = "Currency updated successfully"),
            ApiResponse(responseCode = "404", description = "Currency not found")
        ]
    )
    fun update(
        @PathVariable id: String,
        @RequestBody @Valid request: CurrencyRequest
    ): CurrencyResponse =
        currencyService.update(id, request).let(currencyMapper::toResponse)

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Delete a currency by ID",
        responses = [
            ApiResponse(responseCode = "204", description = "Currency deleted successfully"),
            ApiResponse(responseCode = "404", description = "Currency not found")
        ]
    )
    fun delete(@PathVariable id: String) {
        currencyService.delete(id)
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get paginated list of currencies")
    fun getAllPaged(
        @Parameter(description = "Page number (0-based)", example = "0")
        @RequestParam(defaultValue = "0") page: Int,

        @Parameter(description = "Page size", example = "10")
        @RequestParam(defaultValue = "10") size: Int
    ): PageImpl<CurrencyResponse> {
        val currencies = currencyService.getAllPaged(page, size)
        val responses = currencies.content.map(currencyMapper::toResponse)
        return PageImpl(responses, currencies.pageable, currencies.totalElements)
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all currencies (no pagination)")
    fun getAll(): List<CurrencyResponse> =
        currencyService.getAll().map(currencyMapper::toResponse)
}
