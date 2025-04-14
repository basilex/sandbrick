package com.sandbrick.sbp.api.v1.token.dto

import com.sandbrick.sbp.domain.auth.TokenType
import io.swagger.v3.oas.annotations.media.Schema
import java.time.Instant

@Schema(description = "Response object containing token details")
data class TokenResponse(

    @Schema(description = "Unique identifier of the token", example = "c7f0e0a1e9r0c030")
    val id: String,

    @Schema(description = "JWT token string", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    val token: String,

    @Schema(description = "Type of the token", example = "ACCESS")
    val type: TokenType,

    @Schema(description = "Indicates whether the token is expired", example = "false")
    val expired: Boolean,

    @Schema(description = "Indicates whether the token is revoked", example = "false")
    val revoked: Boolean,

    @Schema(description = "Expiration time of the token", example = "2025-12-31T23:59:59Z")
    val expiryDate: Instant,

    @Schema(description = "ID of the user this token belongs to", example = "c7f0e0a1e9r0vlu2")
    val userId: String
)
