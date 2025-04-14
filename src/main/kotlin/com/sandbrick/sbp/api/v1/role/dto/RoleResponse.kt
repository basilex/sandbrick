package com.sandbrick.sbp.api.v1.role.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Response containing role information")
data class RoleResponse(

    @Schema(
        description = "Unique identifier of the role",
        example = "c7f0e0a1e9r0c030",
        required = true
    )
    val id: String,

    @Schema(
        description = "Name of the role",
        example = "ADMIN",
        required = true
    )
    val name: String
)
