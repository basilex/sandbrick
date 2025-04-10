package com.sandbrick.sbp.api.v1.profile.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "Data for creating or updating a user profile")
data class ProfileRequest(
    @Schema(description = "User's first name", example = "John")
    val firstName: String? = null,

    @Schema(description = "User's last name", example = "Doe")
    val lastName: String? = null,

    @Schema(description = "User's phone number", example = "+380931234567")
    val phone: String? = null,

    @Schema(description = "URL of the user's avatar", example = "https://example.com/avatar.jpg")
    val avatarUrl: String? = null,

    @field:NotBlank
    @Schema(description = "ID of the user who owns this profile", example = "c28j9pmd2jqklrdc9lfg", required = true)
    val userId: String
)