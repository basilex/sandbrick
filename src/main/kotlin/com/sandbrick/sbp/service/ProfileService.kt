package com.sandbrick.sbp.service

import com.sandbrick.sbp.api.v1.profile.dto.ProfileRequest
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

    fun getAll(): List<Profile> =
        profileRepository.findAll()

    fun getById(id: String): Profile =
        profileRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Profile with id $id not found") }

    @Transactional
    fun create(request: ProfileRequest): Profile {
        val user = userRepository.findById(request.userId)
            .orElseThrow { ResourceNotFoundException("User with id ${request.userId} not found") }

        val profile = Profile(
            firstName = request.firstName,
            lastName = request.lastName,
            phone = request.phone,
            avatarUrl = request.avatarUrl,
            user = user
        )
        return profileRepository.save(profile)
    }

    @Transactional
    fun update(id: String, request: ProfileRequest): Profile {
        val profile = profileRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Profile with id $id not found") }

        profile.firstName = request.firstName
        profile.lastName = request.lastName
        profile.phone = request.phone
        profile.avatarUrl = request.avatarUrl

        return profileRepository.save(profile)
    }

    @Transactional
    fun delete(id: String) {
        if (!profileRepository.existsById(id)) {
            throw ResourceNotFoundException("Profile with id $id not found")
        }
        profileRepository.deleteById(id)
    }
}
