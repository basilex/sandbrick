package com.sandbrick.sbp.api.v1.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "User registration request")
data class RegisterRequest(

    @Schema(
        description = "Unique username (used for login)",
        example = "john_doe"
    )
    @field:NotBlank(message = "Username is required")
    @field:Size(min = 4, max = 32, message = "Username must be between 4 and 32 characters")
    val username: String,

    @Schema(
        description = "User's valid email address",
        example = "john.doe@example.com"
    )
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Email must be valid")
    val email: String,

    @Schema(
        description = "Password (minimum 8 characters)",
        example = "StrongPassword123!"
    )
    @field:NotBlank(message = "Password is required")
    @field:Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    val password: String
)
