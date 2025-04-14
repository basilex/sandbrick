package com.sandbrick.sbp.api.v1.profile.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "Data for creating or updating a user profile")
data class ProfileRequest(

    @field:Size(min = 2, max = 64, message = "First name must be between 2 and 64 characters")
    @Schema(description = "User's first name", example = "John")
    val firstName: String? = null,

    @field:Size(min = 2, max = 64, message = "Last name must be between 2 and 64 characters")
    @Schema(description = "User's last name", example = "Doe")
    val lastName: String? = null,

    @field:Size(max = 255, message = "Avatar URL must not exceed 255 characters")
    @Schema(description = "URL of the user's avatar", example = "https://example.com/avatar.jpg")
    val avatarUrl: String? = null,

    @field:NotBlank(message = "User ID is required")
    @Schema(description = "ID of the user who owns this profile", example = "c28j9pmd2jqklrdc9lfg", required = true)
    val userId: String
)
