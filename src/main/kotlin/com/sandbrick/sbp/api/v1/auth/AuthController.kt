package com.sandbrick.sbp.api.v1.auth

import com.sandbrick.sbp.api.v1.auth.dto.AuthResponse
import com.sandbrick.sbp.api.v1.auth.dto.LoginRequest
import com.sandbrick.sbp.api.v1.auth.dto.RefreshRequest
import com.sandbrick.sbp.api.v1.auth.dto.RegisterRequest
import com.sandbrick.sbp.domain.auth.TokenType
import com.sandbrick.sbp.exception.UnauthorizedException
import com.sandbrick.sbp.repository.TokenRepository
import com.sandbrick.sbp.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService,
    private val tokenRepository: TokenRepository
) {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@Valid @RequestBody request: RegisterRequest): AuthResponse =
        authService.register(request)

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): AuthResponse =
        authService.authenticate(request)

    @PostMapping("/refresh")
    fun refreshToken(@RequestBody request: RefreshRequest): AuthResponse {
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
