package com.sandbrick.sbp.api.v1.token

import com.sandbrick.sbp.api.v1.token.dto.TokenResponse
import com.sandbrick.sbp.domain.auth.TokenType
import com.sandbrick.sbp.mapper.TokenMapper
import com.sandbrick.sbp.service.TokenService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.Min
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
    @Operation(summary = "Get all tokens (no pagination)")
    fun getAll(): List<TokenResponse> =
        tokenService.getAll().map(tokenMapper::toResponse)

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Get paginated list of all tokens",
        parameters = [
            Parameter(name = "page", `in` = ParameterIn.QUERY, description = "Page number", example = "0"),
            Parameter(name = "size", `in` = ParameterIn.QUERY, description = "Page size", example = "10")
        ]
    )
    fun getAllPaged(
        @RequestParam(defaultValue = "0") @Min(0) page: Int,
        @RequestParam(defaultValue = "10") @Min(1) size: Int
    ): Page<TokenResponse> =
        tokenService.getAllPaged(page, size).map(tokenMapper::toResponse)

    @GetMapping("/active")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Get paginated list of active (non-expired) tokens",
        parameters = [
            Parameter(name = "page", `in` = ParameterIn.QUERY, description = "Page number", example = "0"),
            Parameter(name = "size", `in` = ParameterIn.QUERY, description = "Page size", example = "10")
        ]
    )
    fun getActiveTokensPaged(
        @RequestParam(defaultValue = "0") @Min(0) page: Int,
        @RequestParam(defaultValue = "10") @Min(1) size: Int
    ): Page<TokenResponse> =
        tokenService.getActiveTokensPaged(page, size).map(tokenMapper::toResponse)

    @GetMapping("/filtered")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Get paginated filtered list of active tokens",
        description = "Optional filters: revoked, token type (ACCESS or REFRESH)",
        parameters = [
            Parameter(name = "page", `in` = ParameterIn.QUERY, description = "Page number", example = "0"),
            Parameter(name = "size", `in` = ParameterIn.QUERY, description = "Page size", example = "10"),
            Parameter(name = "revoked", `in` = ParameterIn.QUERY, description = "Revoked status", example = "false"),
            Parameter(name = "type", `in` = ParameterIn.QUERY, description = "Token type", example = "REFRESH")
        ]
    )
    fun getFilteredActiveTokens(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(required = false) revoked: Boolean?,
        @RequestParam(required = false) type: TokenType?
    ): Page<TokenResponse> =
        tokenService.getFilteredActiveTokens(page, size, revoked, type).map(tokenMapper::toResponse)

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Get all valid (non-revoked, non-expired) tokens for a specific user",
        parameters = [
            Parameter(name = "userId", description = "ID of the user", example = "d1f5a7d2-8b39-47b9-bf1e-2f0d5f1c3d1c")
        ]
    )
    fun getByUser(@PathVariable userId: String): List<TokenResponse> =
        tokenService.getValidTokensByUser(userId).map(tokenMapper::toResponse)

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Delete token by ID",
        parameters = [
            Parameter(name = "id", description = "Token ID to delete", example = "a8fbb7b2-1e90-4ef0-8dbf-6efc5f6e33a7")
        ],
        responses = [
            ApiResponse(responseCode = "204", description = "Token deleted successfully"),
            ApiResponse(responseCode = "404", description = "Token not found")
        ]
    )
    fun delete(@PathVariable id: String) =
        tokenService.deleteById(id)
}
