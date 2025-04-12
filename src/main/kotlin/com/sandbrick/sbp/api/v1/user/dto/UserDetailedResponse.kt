package com.sandbrick.sbp.api.v1.user.dto

import com.sandbrick.sbp.api.v1.contact.dto.ContactResponse
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "User response model")
data class UserDetailedResponse(
    @Schema(description = "User ID", example = "c7f0e0a1e9r0vlu1")
    val id: String,

    @Schema(description = "Username", example = "johndoe")
    val username: String,

    @Schema(description = "Roles assigned to the user", example = "[\"USER\", \"ADMIN\"]")
    val roles: Set<String>,

    @Schema(description = "User's contact methods (email, phone, etc.)")
    val contacts: List<ContactResponse>
)
