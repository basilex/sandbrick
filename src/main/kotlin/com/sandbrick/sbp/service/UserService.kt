package com.sandbrick.sbp.service

import com.sandbrick.sbp.api.v1.user.dto.UserRequest
import com.sandbrick.sbp.config.AppProperties
import com.sandbrick.sbp.domain.User
import com.sandbrick.sbp.exception.DuplicateEntityException
import com.sandbrick.sbp.exception.ResourceNotFoundException
import com.sandbrick.sbp.exception.ValidationException
import com.sandbrick.sbp.repository.RoleRepository
import com.sandbrick.sbp.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val appProperties: AppProperties,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun getAll(): List<User> = userRepository.findAll()

    fun getById(id: String): User =
        userRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("User with id $id not found") }

    @Transactional
    fun create(request: UserRequest): User {
        if (userRepository.existsByUsername(request.username)) {
            throw DuplicateEntityException("Username '${request.username}' already exists")
        }
        if (userRepository.existsByEmail(request.email)) {
            throw DuplicateEntityException("Email '${request.email}' already exists")
        }
        if (request.password.length < appProperties.validation.passwordMinLength) {
            throw ValidationException("Password min length '${appProperties.validation.passwordMinLength}' failed")
        }

        val roles = request.roles.map { roleName ->
            roleRepository.findByName(roleName)
                ?: throw ResourceNotFoundException("Role '$roleName' not found")
        }.toSet()

        val encodedPassword = passwordEncoder.encode(request.password)
        return User(
            username = request.username,
            email = request.email,
            password = encodedPassword,
            roles = roles.toMutableSet()
        ).let { userRepository.save(it) }
    }

    @Transactional
    fun update(id: String, request: UserRequest): User {
        val user = userRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("User with id $id not found") }

        if ((user.username != request.username) && userRepository.existsByUsername(request.username)) {
            throw DuplicateEntityException("Username '${request.username}' already exists")
        }
        if ((user.email != request.email) && userRepository.existsByEmail(request.email)) {
            throw DuplicateEntityException("Email '${request.email}' already exists")
        }

        val roles = request.roles.map { roleName ->
            roleRepository.findByName(roleName)
                ?: throw ResourceNotFoundException("Role '$roleName' not found")
        }.toSet()

        user.username = request.username
        user.email = request.email
        user.password = passwordEncoder.encode(request.password)
        user.roles = roles.toMutableSet()

        return userRepository.save(user)
    }

    @Transactional
    fun delete(id: String) {
        if (!userRepository.existsById(id)) {
            throw ResourceNotFoundException("User with id $id not found")
        }
        userRepository.deleteById(id)
    }

    fun findByUsername(username: String): User =
        userRepository.findByUsername(username)
            ?: throw ResourceNotFoundException("User with username '$username' not found")
}
