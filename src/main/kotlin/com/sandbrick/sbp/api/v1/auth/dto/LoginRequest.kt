package com.sandbrick.sbp.api.v1.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "User login request")
data class LoginRequest(
    @Schema(description = "User's username", example = "admin")
    @field:NotBlank(message = "Username is required")
    val username: String,

    @Schema(description = "User's password", example = "MySecret123!")
    @field:Size(min = 6, max = 255, message = "Password must be 6-255 characters long")
    val password: String
)