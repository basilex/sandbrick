package com.sandbrick.sbp.mapper

import com.sandbrick.sbp.api.v1.user.dto.UserResponse
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
}
