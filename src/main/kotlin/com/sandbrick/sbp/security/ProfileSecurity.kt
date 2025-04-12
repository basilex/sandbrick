package com.sandbrick.sbp.security.access

import com.sandbrick.sbp.repository.ProfileRepository
import org.springframework.stereotype.Component

@Component("profileSecurity")
class ProfileSecurity(
    private val profileRepository: ProfileRepository
) {
    fun isOwner(profileId: String, username: String): Boolean =
        profileRepository.findById(profileId)
            .map { it.user.username == username }
            .orElse(false)
}
