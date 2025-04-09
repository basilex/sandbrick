package com.sandbrick.sbp.api.v1.role.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RoleRequest(
    @field:NotBlank(message = "Role name must not be blank")
    @field:Size(min = 2, max = 250, message = "Role name must be between 2 and 250 characters")
    val name: String
)
