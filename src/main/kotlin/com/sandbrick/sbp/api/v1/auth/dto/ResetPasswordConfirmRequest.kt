package com.sandbrick.sbp.api.v1.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "Confirm password reset using a token")
data class ResetPasswordConfirmRequest(

    @field:NotBlank(message = "Reset token is required")
    @Schema(description = "Token received by email", example = "f5b91d82ab764db2")
    val token: String,

    @field:NotBlank(message = "New password is required")
    @field:Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    @Schema(description = "New password to be set", example = "newStrongPassword123")
    val newPassword: String
)
