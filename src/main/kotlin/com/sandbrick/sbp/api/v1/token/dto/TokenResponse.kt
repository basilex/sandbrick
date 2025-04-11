package com.sandbrick.sbp.api.v1.token.dto

import com.sandbrick.sbp.domain.auth.TokenType
import java.time.Instant

data class TokenResponse(
    val id: String,
    val token: String,
    val type: TokenType,
    val expired: Boolean,
    val revoked: Boolean,
    val expiryDate: Instant,
    val userId: String
)
