package com.sandbrick.sbp.service

import com.sandbrick.sbp.api.v1.profile.dto.ProfileRequest
import com.sandbrick.sbp.api.v1.profile.dto.ProfileResponse
import com.sandbrick.sbp.domain.Profile
import com.sandbrick.sbp.exception.ResourceNotFoundException
import com.sandbrick.sbp.repository.ProfileRepository
import com.sandbrick.sbp.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProfileService(
    private val profileRepository: ProfileRepository,
    private val userRepository: UserRepository
) {

    fun getById(id: String): ProfileResponse =
        profileRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Profile not found") }
            .toResponse()

    fun getAll(): List<ProfileResponse> =
        profileRepository.findAll().map { it.toResponse() }

    @Transactional
    fun create(request: ProfileRequest): ProfileResponse {
        val user = userRepository.findById(request.userId)
            .orElseThrow { ResourceNotFoundException("User not found") }

        val profile = Profile(
            firstName = request.firstName,
            lastName = request.lastName,
            phone = request.phone,
            avatarUrl = request.avatarUrl,
            user = user
        )

        return profileRepository.save(profile).toResponse()
    }

    @Transactional
    fun update(id: String, request: ProfileRequest): ProfileResponse {
        val profile = profileRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Profile not found") }

        profile.firstName = request.firstName
        profile.lastName = request.lastName
        profile.phone = request.phone
        profile.avatarUrl = request.avatarUrl

        return profileRepository.save(profile).toResponse()
    }

    @Transactional
    fun delete(id: String) {
        val profile = profileRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Profile not found") }
        profileRepository.delete(profile)
    }

    private fun Profile.toResponse() = ProfileResponse(
        id = id,
        firstName = firstName,
        lastName = lastName,
        phone = phone,
        avatarUrl = avatarUrl,
        userId = userId
    )
}
