package com.sandbrick.sbp.api.v1.user.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

@Schema(description = "Request object for creating or updating a user")
data class UserRequest(

    @field:NotBlank(message = "Username is required")
    @field:Size(min = 4, max = 64, message = "Username must be from 4 to 64 characters")
    @Schema(
        description = "Unique username",
        example = "johndoe",
        minLength = 4,
        maxLength = 64,
        required = true
    )
    val username: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 8, max = 255, message = "Password must be from 8 to 255 characters")
    @Schema(
        description = "Raw password (will be hashed)",
        example = "strongpassword123",
        minLength = 8,
        maxLength = 255,
        required = true
    )
    val password: String,

    @field:NotEmpty(message = "At least one role is required")
    @Schema(
        description = "Set of role names to assign to the user",
        example = "[\"USER\", \"ADMIN\"]",
        required = true
    )
    val roles: Set<String>
)
