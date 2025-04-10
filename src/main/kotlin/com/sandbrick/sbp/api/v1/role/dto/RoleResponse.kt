package com.sandbrick.sbp.api.v1.role.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Role response model")
data class RoleResponse(
    @Schema(description = "Unique identifier of the role", example = "d1f5a7d2-8b39-47b9-bf1e-2f0d5f1c3d1c")
    val id: String,

    @Schema(description = "Name of the role", example = "ADMIN")
    val name: String
)
