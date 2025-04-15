package com.sandbrick.sbp.api.v1.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "Request to confirm email verification using the received token")
data class EmailVerificationConfirmRequest(

    @field:NotBlank(message = "Verification token is required")
    @field:Size(min = 6, max = 64, message = "Token must be between 6 and 64 characters")
    @Schema(description = "Verification token string", example = "x7f0e0a1e9r0abc1", minLength = 6, maxLength = 64)
    val token: String
)
