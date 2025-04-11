package com.sandbrick.sbp.mapper

import com.sandbrick.sbp.api.v1.user.dto.UserRequest
import com.sandbrick.sbp.api.v1.user.dto.UserResponse
import com.sandbrick.sbp.domain.Role
import com.sandbrick.sbp.domain.User
import org.springframework.stereotype.Component

@Component
class UserMapper {
    fun toResponse(user: User): UserResponse =
        UserResponse(
            id = user.id,
            username = user.username,
            email = user.email,
            roles = user.roles.map { it.name }.toSet()
        )

    fun toEntity(
        request: UserRequest,
        encodedPassword: String,
        roles: Set<Role>
    ): User = User(
        username = request.username,
        email = request.email,
        password = encodedPassword,
        roles = roles.toMutableSet()
    )
}
