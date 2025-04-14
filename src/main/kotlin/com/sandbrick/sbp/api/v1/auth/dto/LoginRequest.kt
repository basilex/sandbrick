package com.sandbrick.sbp.api.v1.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "Authentication request payload for user login")
data class LoginRequest(

    @field:NotBlank(message = "Username is required")
    @field:Size(min = 3, max = 64, message = "Username must be between 3 and 64 characters")
    @Schema(
        description = "Username of the user",
        example = "admin",
        minLength = 3,
        maxLength = 64,
        required = true
    )
    val username: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 6, max = 255, message = "Password must be between 6 and 255 characters")
    @Schema(
        description = "Password of the user",
        example = "MySecret123!",
        minLength = 6,
        maxLength = 255,
        required = true
    )
    val password: String
)
