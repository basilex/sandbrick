package com.sandbrick.sbp.service

import com.sandbrick.sbp.api.v1.user.dto.UserRequest
import com.sandbrick.sbp.api.v1.user.dto.UserDetailedResponse
import com.sandbrick.sbp.api.v1.user.dto.UserSummaryResponse
import com.sandbrick.sbp.config.AppProperties
import com.sandbrick.sbp.exception.DuplicateEntityException
import com.sandbrick.sbp.exception.ResourceNotFoundException
import com.sandbrick.sbp.exception.ValidationException
import com.sandbrick.sbp.mapper.UserMapper
import com.sandbrick.sbp.repository.RoleRepository
import com.sandbrick.sbp.repository.UserRepository
import org.springframework.data.domain.Sort
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val appProperties: AppProperties,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userMapper: UserMapper
) {
    fun getAllSummaries(): List<UserSummaryResponse> =
        userRepository
            .findAll(Sort.by("username").ascending())
            .map(userMapper::toSummary)

    fun getAllDetailed(): List<UserDetailedResponse> =
        userRepository
            .findAll(Sort.by("username").ascending())
            .map(userMapper::toDetailed)

    fun getById(id: String): UserDetailedResponse =
        userRepository
            .findById(id)
            .orElseThrow { ResourceNotFoundException("User with id $id not found") }
            .let(userMapper::toDetailed)

    @Transactional
    fun create(request: UserRequest): UserDetailedResponse {
        if (userRepository.existsByUsername(request.username)) {
            throw DuplicateEntityException("Username '${request.username}' already exists")
        }

        if (request.password.length < appProperties.validation.passwordMinLength) {
            throw ValidationException("Password min length '${appProperties.validation.passwordMinLength}' failed")
        }
        val roles = getRolesFromRequest(request.roles)
        val encodedPassword = passwordEncoder.encode(request.password)

        val user = userMapper.toEntity(request, roles, encodedPassword)
        return userRepository.save(user).let(userMapper::toDetailed)
    }

    @Transactional
    fun update(id: String, request: UserRequest): UserDetailedResponse {
        val user = userRepository
            .findById(id)
            .orElseThrow { ResourceNotFoundException("User with id $id not found") }

        if (user.username != request.username && userRepository.existsByUsername(request.username)) {
            throw DuplicateEntityException("Username '${request.username}' already exists")
        }
        val roles = getRolesFromRequest(request.roles)
        val encodedPassword = passwordEncoder.encode(request.password)

        user.username = request.username
        user.password = encodedPassword
        user.roles = roles.toMutableSet()

        return userRepository.save(user).let(userMapper::toDetailed)
    }

    @Transactional
    fun delete(id: String) {
        if (!userRepository.existsById(id)) {
            throw ResourceNotFoundException("User with id $id not found")
        }
        userRepository.deleteById(id)
    }

    fun findByUsername(username: String): UserDetailedResponse =
        userRepository.findByUsername(username)
            ?.let(userMapper::toDetailed)
            ?: throw ResourceNotFoundException("User '$username' not found")

    private fun getRolesFromRequest(roleNames: Set<String>) =
        roleNames.map { roleName ->
            roleRepository.findByName(roleName)
                ?: throw ResourceNotFoundException("Role '$roleName' not found")
        }.toSet()
}
