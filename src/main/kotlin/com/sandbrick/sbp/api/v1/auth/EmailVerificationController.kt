package com.sandbrick.sbp.api.v1.auth

import com.sandbrick.sbp.api.v1.auth.dto.EmailVerificationConfirmRequest
import com.sandbrick.sbp.api.v1.auth.dto.EmailVerificationRequest
import com.sandbrick.sbp.exception.ValidationException
import com.sandbrick.sbp.service.EmailVerificationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/api/v1/auth/verify-email")
@Tag(
    name = "Email Verification",
    description = "Endpoints for sending and confirming email verification links"
)
class EmailVerificationController(
    private val emailVerificationService: EmailVerificationService
) {

    @ResponseBody
    @PostMapping("/request")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Send email verification token",
        description = "Sends a verification token to the email provided in the user's contact list.",
        requestBody = SwaggerRequestBody(
            required = true,
            content = [Content(schema = Schema(implementation = EmailVerificationRequest::class))]
        ),
        responses = [
            ApiResponse(responseCode = "200", description = "Verification token sent successfully"),
            ApiResponse(responseCode = "404", description = "Email not found")
        ]
    )
    fun requestVerification(@Valid @RequestBody request: EmailVerificationRequest): Map<String, String> {
        val token = emailVerificationService.createVerificationToken(request)
        return mapOf("token" to token) // Useful for development/debugging
    }

    @ResponseBody
    @PostMapping("/confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Confirm email verification (POST)",
        description = "Accepts a verification token and confirms the associated email address.",
        requestBody = SwaggerRequestBody(
            required = true,
            content = [Content(schema = Schema(implementation = EmailVerificationConfirmRequest::class))]
        ),
        responses = [
            ApiResponse(responseCode = "204", description = "Email verified successfully"),
            ApiResponse(responseCode = "400", description = "Invalid or expired token")
        ]
    )
    fun confirmVerification(@Valid @RequestBody request: EmailVerificationConfirmRequest) {
        emailVerificationService.confirmVerification(request)
    }

    @GetMapping("/confirm")
    @Operation(hidden = true) // скрываем из Swagger
    fun confirmViaBrowser(@RequestParam token: String, model: Model): String {
        return try {
            emailVerificationService.confirmVerification(EmailVerificationConfirmRequest(token))
            "email/verify_success"
        } catch (ex: ValidationException) {
            "email/verify_failed"
        }
    }
}
