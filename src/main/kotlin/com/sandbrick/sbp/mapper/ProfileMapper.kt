package com.sandbrick.sbp.mapper

import com.sandbrick.sbp.api.v1.profile.dto.ProfileRequest
import com.sandbrick.sbp.api.v1.profile.dto.ProfileResponse
import com.sandbrick.sbp.domain.Profile
import com.sandbrick.sbp.domain.User
import org.springframework.stereotype.Component

@Component
class ProfileMapper {

    fun toResponse(profile: Profile): ProfileResponse =
        ProfileResponse(
            id = profile.id,
            firstName = profile.firstName,
            lastName = profile.lastName,
            phone = profile.phone,
            avatarUrl = profile.avatarUrl,
            userId = profile.user.id
        )

    fun toEntity(request: ProfileRequest, user: User): Profile =
        Profile(
            firstName = request.firstName,
            lastName = request.lastName,
            phone = request.phone,
            avatarUrl = request.avatarUrl,
            user = user
        )
}
