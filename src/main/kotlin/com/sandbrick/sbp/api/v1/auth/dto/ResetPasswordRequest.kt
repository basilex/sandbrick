package com.sandbrick.sbp.api.v1.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "Request to initiate password reset")
data class ResetPasswordRequest(
    @field:NotBlank(message = "Username is required")
    @field:Size(min = 3, max = 64, message = "Username must be between 3 and 64 characters")
    @Schema(description = "Username of the account", example = "johndoe")
    val username: String
)
