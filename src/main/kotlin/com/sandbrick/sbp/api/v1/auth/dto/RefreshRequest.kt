package com.sandbrick.sbp.api.v1.auth.dto

import jakarta.validation.constraints.NotBlank

data class RefreshRequest(
    @field:NotBlank(message = "Token is required")
    val refreshToken: String
)