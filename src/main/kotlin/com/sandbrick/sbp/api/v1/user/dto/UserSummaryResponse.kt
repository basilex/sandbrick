package com.sandbrick.sbp.api.v1.user.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Basic user info, used for listings or compact representations")
data class UserSummaryResponse(

    @Schema(
        description = "Unique identifier of the user",
        example = "c7f0e0a1e9r0vlu1",
        required = true
    )
    val id: String,

    @Schema(
        description = "Username of the user",
        example = "johndoe",
        required = true
    )
    val username: String,

    @Schema(
        description = "Set of roles assigned to the user",
        example = "[\"USER\", \"ADMIN\"]",
        required = true
    )
    val roles: Set<String>
)
