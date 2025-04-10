package com.sandbrick.sbp.exception

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.OffsetDateTime

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, Any>> {
        val errors = ex.bindingResult.fieldErrors.associate { it.field to it.defaultMessage }
        return buildErrorResponse(
            status = HttpStatus.BAD_REQUEST,
            error = "Validation failed",
            message = errors,
            code = "VALIDATION_ERROR"
        )
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(ex: ConstraintViolationException): ResponseEntity<Map<String, Any>> {
        val errors = ex.constraintViolations.associate {
            val fieldPath = it.propertyPath.toString()
            fieldPath to it.message
        }
        return buildErrorResponse(
            status = HttpStatus.BAD_REQUEST,
            error = "Constraint violation",
            message = errors,
            code = "CONSTRAINT_VIOLATION"
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleInvalidJson(ex: HttpMessageNotReadableException): ResponseEntity<Map<String, Any>> {
        val message = when (val rootCause = ex.rootCause) {
            is MismatchedInputException -> {
                val path = rootCause.path.joinToString(".") { it.fieldName ?: "?" }
                "Invalid or missing value for field: $path"
            }
            else -> rootCause?.message ?: "Malformed JSON request"
        }
        return buildErrorResponse(
            status = HttpStatus.BAD_REQUEST,
            error = "Invalid request",
            message = message,
            code = "INVALID_JSON"
        )
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(ex: ResourceNotFoundException): ResponseEntity<Map<String, Any>> {
        return buildErrorResponse(
            status = HttpStatus.NOT_FOUND,
            error = "Not Found",
            message = ex.message ?: "Resource not found",
            code = "RESOURCE_NOT_FOUND"
        )
    }

    @ExceptionHandler(DuplicateEntityException::class)
    fun handleNotFound(ex: DuplicateEntityException): ResponseEntity<Map<String, Any>> {
        return buildErrorResponse(
            status = HttpStatus.CONFLICT,
            error = "Duplicate entity",
            message = "Entity already exists",
            code = "DUPLICATE_ENTITY"
        )
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnathorized(ex: UnauthorizedException): ResponseEntity<Map<String, Any>> {
        return buildErrorResponse(
            status = HttpStatus.UNAUTHORIZED,
            error = "Unauthorized",
            message = "Unauthorized",
            code = "UNAUTHORIZED"
        )
    }

    private fun buildErrorResponse(
        status: HttpStatus,
        error: String,
        message: Any,
        code: String = "UNEXPECTED_ERROR"
    ): ResponseEntity<Map<String, Any>> {
        val exceptionBody = mapOf(
            "status" to status.value(),
            "code" to code,
            "error" to error,
            "message" to message,
            "timestamp" to OffsetDateTime.now().toString()
        )
        return ResponseEntity.status(status).body(
            mapOf("exception" to exceptionBody)
        )
    }
}
