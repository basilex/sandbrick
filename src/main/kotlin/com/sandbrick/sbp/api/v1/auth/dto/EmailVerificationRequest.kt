package com.sandbrick.sbp.api.v1.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "Request to initiate email verification for a user")
data class EmailVerificationRequest(

    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Email must be valid")
    @field:Size(max = 255, message = "Email must not exceed 255 characters")
    @Schema(description = "Email address to verify", example = "john.doe@example.com", maxLength = 255)
    val email: String
)
