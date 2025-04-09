package com.sandbrick.sbp.api.v1.auth

import com.sandbrick.sbp.api.v1.auth.dto.AuthResponse
import com.sandbrick.sbp.api.v1.auth.dto.LoginRequest
import com.sandbrick.sbp.api.v1.auth.dto.RegisterRequest
import com.sandbrick.sbp.service.auth.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@Valid @RequestBody request: RegisterRequest): AuthResponse =
        authService.register(request)

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): AuthResponse =
        authService.authenticate(request)
}
