package com.sandbrick.sbp.api.v1.auth.dto

import io.swagger.v3.oas.annotations.media.Schema

data class AuthResponse(
    @Schema(description = "Access token")
    val accessToken: String,

    @Schema(description = "Refresh token")
    val refreshToken: String,

    @Schema(description = "Token type")
    val tokenType: String = "Bearer"
)