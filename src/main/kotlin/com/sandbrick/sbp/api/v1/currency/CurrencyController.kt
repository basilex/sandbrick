package com.sandbrick.sbp.api.v1.currency

import com.sandbrick.sbp.api.v1.currency.dto.CurrencyRequest
import com.sandbrick.sbp.service.CurrencyService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/currencies")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Currency", description = "Currency management operations")
class CurrencyController(
    private val currencyService: CurrencyService
) {
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new currency")
    fun create(@RequestBody @Valid request: CurrencyRequest) =
        currencyService.create(request)

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update an existing currency")
    fun update(@PathVariable id: String, @RequestBody @Valid request: CurrencyRequest) =
        currencyService.update(id, request)

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a currency by ID")
    fun delete(@PathVariable id: String) =
        currencyService.delete(id)

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all currencies")
    fun getAll() = currencyService.getAll()
}
