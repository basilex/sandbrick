package com.sandbrick.sbp.api.v1.role.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "Request object for creating or updating a role")
data class RoleRequest(
    @field:NotBlank(message = "Role name must not be blank")
    @field:Size(min = 2, max = 250, message = "Role name must be between 2 and 250 characters")
    @Schema(description = "Unique name of the role", example = "ADMIN")
    val name: String
)
