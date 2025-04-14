package com.sandbrick.sbp.api.v1.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "Request to change password using reset token")
data class ChangePasswordRequest(
    @field:NotBlank(message = "Reset token is required")
    @Schema(description = "Reset token sent via email", example = "abc123-reset-token")
    val token: String,

    @field:NotBlank(message = "New password is required")
    @field:Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    @Schema(description = "New password", example = "MyNewStrongPass123!")
    val newPassword: String
)
