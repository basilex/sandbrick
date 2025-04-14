package com.sandbrick.sbp.api.v1.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@Schema(description = "Request to initiate password reset via email")
data class ResetPasswordRequest(

    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Email must be valid")
    @Schema(description = "Email address to receive reset link", example = "john.doe@example.com")
    val email: String
)
