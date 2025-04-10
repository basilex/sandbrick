package com.sandbrick.sbp.api.v1.profile.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Response containing user profile information")
data class ProfileResponse(

    @Schema(description = "Profile ID", example = "c28j9pmd2jqklrdc9lf0")
    val id: String,

    @Schema(description = "User's first name", example = "John")
    val firstName: String?,

    @Schema(description = "User's last name", example = "Doe")
    val lastName: String?,

    @Schema(description = "User's phone number", example = "+380931234567")
    val phone: String?,

    @Schema(description = "URL of the user's avatar", example = "https://example.com/avatar.jpg")
    val avatarUrl: String?,

    @Schema(description = "ID of the user who owns this profile", example = "c28j9pmd2jqklrdc9lfg")
    val userId: String
)