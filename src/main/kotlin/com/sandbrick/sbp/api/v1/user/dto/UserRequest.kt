package com.sandbrick.sbp.api.v1.user.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserRequest(
    @field:NotBlank(message = "Username is required")
    @field:Size(min = 4, max = 64)
    val username: String,

    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Email must be valid")
    @field:Size(min = 4, max = 255)
    val email: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 8, max = 255)
    val password: String,

    @field:NotBlank(message = "At least one role is required")
    val roles: Set<String>
)
