package com.sandbrick.sbp.api.common.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Standard structure for error responses returned by the API")
data class ErrorResponse(

    @Schema(
        description = "HTTP status code",
        example = "400",
        required = true
    )
    val status: Int,

    @Schema(
        description = "Application-specific error code",
        example = "VALIDATION_ERROR",
        required = true
    )
    val code: String,

    @Schema(
        description = "Brief description of the error",
        example = "Validation failed",
        required = true
    )
    val error: String,

    @Schema(
        description = "Detailed message or validation errors",
        example = "{\"username\": \"must not be blank\"}",
        type = "object"
    )
    val message: Any,

    @Schema(
        description = "Time the error occurred in ISO 8601 format",
        example = "2025-04-09T14:55:10.456Z",
        required = true
    )
    val timestamp: String
)
