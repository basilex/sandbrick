package com.sandbrick.sbp.service

import com.sandbrick.sbp.api.v1.auth.dto.AuthResponse
import com.sandbrick.sbp.api.v1.auth.dto.LoginRequest
import com.sandbrick.sbp.api.v1.auth.dto.RegisterRequest
import com.sandbrick.sbp.domain.User
import com.sandbrick.sbp.domain.auth.TokenType
import com.sandbrick.sbp.exception.DuplicateEntityException
import com.sandbrick.sbp.exception.ResourceNotFoundException
import com.sandbrick.sbp.repository.RoleRepository
import com.sandbrick.sbp.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
    private val tokenService: TokenService
) {

    fun register(request: RegisterRequest): AuthResponse {
        if (userRepository.existsByUsername(request.username)) {
            throw DuplicateEntityException("Username already exists")
        }
        if (userRepository.existsByEmail(request.email)) {
            throw DuplicateEntityException("Email already exists")
        }
        val defaultRole = roleRepository.findByName("USER")
            ?: throw ResourceNotFoundException("Default role USER not found")

        val user = User(
            username = request.username,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            roles = mutableSetOf(defaultRole)
        )
        val savedUser = userRepository.save(user)
        return generateTokensAndSave(savedUser)
    }

    fun authenticate(request: LoginRequest): AuthResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.username, request.password)
        )
        val user = userRepository.findByUsername(request.username)
            ?: throw ResourceNotFoundException("User not found")

        tokenService.revokeAllUserTokens(user)
        return generateTokensAndSave(user)
    }

    fun generateTokensAndSave(user: User): AuthResponse {
        tokenService.revokeAllUserTokens(user)

        val accessToken = jwtService.generateToken(user, TokenType.ACCESS)
        val refreshToken = jwtService.generateToken(user, TokenType.REFRESH)

        val now = Instant.now()
        val accessExpiry = now.plusSeconds(60 * 15) // 15 мин
        val refreshExpiry = now.plusSeconds(60 * 60 * 24 * 7) // 7 дней

        tokenService.saveToken(user, accessToken, TokenType.ACCESS, accessExpiry)
        tokenService.saveToken(user, refreshToken, TokenType.REFRESH, refreshExpiry)

        return AuthResponse(accessToken, refreshToken)
    }
}
