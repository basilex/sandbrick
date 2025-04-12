package com.sandbrick.sbp.mapper

import com.sandbrick.sbp.api.v1.user.dto.UserDetailedResponse
import com.sandbrick.sbp.api.v1.user.dto.UserRequest
import com.sandbrick.sbp.api.v1.user.dto.UserSummaryResponse
import com.sandbrick.sbp.domain.Role
import com.sandbrick.sbp.domain.User
import org.springframework.stereotype.Component

@Component
class UserMapper(
    private val profileMapper: ProfileMapper,
    private val contactMapper: ContactMapper
) {
    fun toSummary(user: User): UserSummaryResponse = UserSummaryResponse(
        id = user.id,
        username = user.username,
        roles = user.roles.map { it.name }.toSet()
    )

    fun toDetailed(user: User): UserDetailedResponse = UserDetailedResponse(
        id = user.id,
        username = user.username,
        roles = user.roles.map { it.name }.toSet(),
        contacts = user.contacts.map(contactMapper::toResponse)
    )

    fun toEntity(request: UserRequest, roles: Set<Role>, hashedPassword: String): User =
        User(
            username = request.username,
            password = hashedPassword,
            roles = roles.toMutableSet()
        )
}
