package com.sandbrick.sbp.mapper

import com.sandbrick.sbp.api.v1.role.dto.request.RoleRequest
import com.sandbrick.sbp.api.v1.role.dto.response.RoleResponse
import com.sandbrick.sbp.domain.Role

import org.springframework.stereotype.Component

@Component
class RoleMapper {
    fun toResponse(role: Role): RoleResponse =
        RoleResponse(
            id = role.id,
            name = role.name
        )

    fun toEntity(request: RoleRequest): Role =
        Role(
            name = request.name
        )
}
