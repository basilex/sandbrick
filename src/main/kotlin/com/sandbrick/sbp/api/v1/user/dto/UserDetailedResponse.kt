package com.sandbrick.sbp.api.v1.user.dto

import com.sandbrick.sbp.api.v1.contact.dto.ContactResponse
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Full user profile including roles and contact methods")
data class UserDetailedResponse(

    @Schema(
        description = "Unique identifier of the user",
        example = "c7f0e0a1e9r0vlu1",
        required = true
    )
    val id: String,

    @Schema(
        description = "Username associated with the user",
        example = "johndoe",
        required = true
    )
    val username: String,

    @Schema(
        description = "Set of roles assigned to the user",
        example = "[\"USER\", \"ADMIN\"]",
        required = true
    )
    val roles: Set<String>,

    @Schema(
        description = "List of user's contact methods (e.g., email, phone)",
        required = true
    )
    val contacts: List<ContactResponse>
)
