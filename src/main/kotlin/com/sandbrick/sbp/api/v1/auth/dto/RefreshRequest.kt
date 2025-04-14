package com.sandbrick.sbp.api.v1.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "Request to refresh JWT tokens")
data class RefreshRequest(

    @Schema(
        description = "Refresh token used to obtain a new access token",
        example = "dGhpcy1pcy1hLXJlZnJlc2gtdG9rZW4=",
        required = true
    )
    @field:NotBlank(message = "Refresh token is required")
    val refreshToken: String
)
