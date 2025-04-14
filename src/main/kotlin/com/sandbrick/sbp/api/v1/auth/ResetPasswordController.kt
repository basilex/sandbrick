package com.sandbrick.sbp.api.v1.auth

import com.sandbrick.sbp.api.v1.auth.dto.ResetPasswordConfirmRequest
import com.sandbrick.sbp.api.v1.auth.dto.ResetPasswordRequest
import com.sandbrick.sbp.service.ResetPasswordService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth/reset-password")
@Tag(name = "Password Reset", description = "Operations for requesting and confirming password resets")
class ResetPasswordController(
    private val resetPasswordService: ResetPasswordService
) {

    @PostMapping("/request")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Request password reset",
        description = "Initiates the password reset flow by generating a reset token and (normally) sending it via email."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Reset token generated successfully"),
            ApiResponse(responseCode = "400", description = "Validation error", content = [Content()])
        ]
    )
    fun requestToken(
        @RequestBody
        @Valid
        @SwaggerRequestBody(
            required = true,
            description = "Email to receive reset token",
            content = [Content(schema = Schema(implementation = ResetPasswordRequest::class))]
        )
        request: ResetPasswordRequest
    ): Map<String, String> {
        val token = resetPasswordService.generateResetToken(request)
        return mapOf("token" to token)
    }

    @PostMapping("/confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Confirm password reset",
        description = "Confirms the reset using the token and sets a new password"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Password reset successful"),
            ApiResponse(responseCode = "400", description = "Validation error or expired token", content = [Content()])
        ]
    )
    fun confirmReset(
        @RequestBody
        @Valid
        @SwaggerRequestBody(
            required = true,
            description = "Reset token and new password",
            content = [Content(schema = Schema(implementation = ResetPasswordConfirmRequest::class))]
        )
        request: ResetPasswordConfirmRequest
    ) {
        resetPasswordService.confirmReset(request)
    }
}
