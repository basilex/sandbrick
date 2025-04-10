package com.sandbrick.sbp.api.common.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Standard structure for API error responses")
data class ErrorResponse(
    @Schema(description = "HTTP status code", example = "400")
    val status: Int,

    @Schema(description = "Machine-readable error code", example = "VALIDATION_ERROR")
    val code: String,

    @Schema(description = "Short description of the error", example = "Validation failed")
    val error: String,

    @Schema(
        description = "Detailed error information (string or map of field errors)",
        example = "{\"username\": \"must not be blank\"}"
    )
    val message: Any,

    @Schema(description = "Timestamp in ISO 8601 format", example = "2025-04-09T14:55:10.456Z")
    val timestamp: String
)
