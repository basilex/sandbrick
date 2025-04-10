package com.sandbrick.sbp.api.v1.user.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "User response model")
data class UserResponse(
    @Schema(description = "Unique identifier of the user", example = "a8fbb7b2-1e90-4ef0-8dbf-6efc5f6e33a7")
    val id: String,

    @Schema(description = "Username", example = "johndoe")
    val username: String,

    @Schema(description = "User email", example = "john@example.com")
    val email: String,

    @Schema(description = "Roles assigned to the user", example = "[\"USER\", \"ADMIN\"]")
    val roles: Set<String>
)
