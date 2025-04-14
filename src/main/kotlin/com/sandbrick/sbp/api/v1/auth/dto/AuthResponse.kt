package com.sandbrick.sbp.api.v1.auth.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Response object containing JWT access and refresh tokens")
data class AuthResponse(

    @Schema(
        description = "JWT access token used for authenticated requests",
        example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        required = true
    )
    val accessToken: String,

    @Schema(
        description = "JWT refresh token used to obtain a new access token",
        example = "dGhpcy1pcy1hLXJlZnJlc2gtdG9rZW4=",
        required = true
    )
    val refreshToken: String,

    @Schema(
        description = "Type of the access token",
        example = "Bearer",
        defaultValue = "Bearer",
        required = true
    )
    val tokenType: String = "Bearer"
)
