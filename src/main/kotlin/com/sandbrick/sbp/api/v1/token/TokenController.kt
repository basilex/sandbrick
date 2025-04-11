package com.sandbrick.sbp.api.v1.token

import com.sandbrick.sbp.api.v1.token.dto.TokenResponse
import com.sandbrick.sbp.mapper.TokenMapper
import com.sandbrick.sbp.service.TokenService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/tokens")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Token", description = "Token management operations")
class TokenController(
    private val tokenService: TokenService,
    private val tokenMapper: TokenMapper
) {

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all tokens")
    fun getAll(): List<TokenResponse> =
        tokenService.getAll().map(tokenMapper::toResponse)

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get paginated list of tokens")
    fun getAllPaged(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Page<TokenResponse> =
        tokenService.getAllPaged(page, size).map(tokenMapper::toResponse)

    @GetMapping("/active")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get paginated list of active (non-expired) tokens")
    fun getActiveTokensPaged(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Page<TokenResponse> =
        tokenService.getActiveTokensPaged(page, size).map(tokenMapper::toResponse)

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get valid tokens by user ID")
    fun getByUser(@PathVariable userId: String): List<TokenResponse> =
        tokenService.getValidTokensByUser(userId).map(tokenMapper::toResponse)

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete token by ID")
    fun delete(@PathVariable id: String) =
        tokenService.deleteById(id)
}
