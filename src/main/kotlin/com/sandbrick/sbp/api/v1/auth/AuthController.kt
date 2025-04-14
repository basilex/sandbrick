package com.sandbrick.sbp.api.v1.auth

import com.sandbrick.sbp.api.v1.auth.dto.*
import com.sandbrick.sbp.domain.auth.TokenType
import com.sandbrick.sbp.exception.UnauthorizedException
import com.sandbrick.sbp.repository.TokenRepository
import com.sandbrick.sbp.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "Authentication and token management")
class AuthController(
    private val authService: AuthService,
    private val tokenRepository: TokenRepository
) {
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Register a new user",
        requestBody = RequestBody(
            required = true,
            content = [Content(schema = Schema(implementation = RegisterRequest::class))]
        ),
        responses = [
            ApiResponse(responseCode = "201", description = "User successfully registered"),
            ApiResponse(responseCode = "400", description = "Validation error")
        ]
    )
    fun register(@Valid @RequestBody request: RegisterRequest): AuthResponse =
        authService.register(request)

    @PostMapping("/login")
    @Operation(
        summary = "Authenticate user and return tokens",
        requestBody = RequestBody(
            required = true,
            content = [Content(schema = Schema(implementation = LoginRequest::class))]
        ),
        responses = [
            ApiResponse(responseCode = "200", description = "Authenticated successfully"),
            ApiResponse(responseCode = "401", description = "Invalid credentials")
        ]
    )
    fun login(@Valid @RequestBody request: LoginRequest): AuthResponse =
        authService.authenticate(request)

    @PostMapping("/logout")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
        summary = "Revoke the current access token",
        parameters = [Parameter(name = "Authorization", description = "Bearer access token", required = true)],
        responses = [
            ApiResponse(responseCode = "200", description = "Token revoked successfully"),
            ApiResponse(responseCode = "401", description = "Invalid token")
        ]
    )
    fun logout(
        @RequestHeader("Authorization") authHeader: String
    ) {
        val token = authHeader.removePrefix("Bearer ").trim()
        if (token.isBlank()) {
            throw UnauthorizedException("Missing or malformed Authorization header")
        }
        val tokenEntity = tokenRepository.findByToken(token)
            ?: throw UnauthorizedException("Invalid access token")

        tokenEntity.expired = true
        tokenEntity.revoked = true
        tokenRepository.save(tokenEntity)
    }

    @PostMapping("/refresh")
    @Operation(
        summary = "Refresh access token using a refresh token",
        requestBody = RequestBody(
            required = true,
            content = [Content(schema = Schema(implementation = RefreshRequest::class))]
        ),
        responses = [
            ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
            ApiResponse(responseCode = "401", description = "Refresh token invalid or expired")
        ]
    )
    fun refreshToken(@Valid @RequestBody request: RefreshRequest): AuthResponse {
        val tokenEntity = tokenRepository.findByTokenAndType(request.refreshToken, TokenType.REFRESH)
            ?: throw UnauthorizedException("Invalid refresh token")

        if (tokenEntity.expiryDate.isBefore(Instant.now())) {
            throw UnauthorizedException("Refresh token expired")
        }
        val user = tokenEntity.user
        tokenRepository.delete(tokenEntity)
        return authService.generateTokensAndSave(user)
    }
}
