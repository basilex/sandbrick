package com.sandbrick.sbp.service

import com.sandbrick.sbp.api.v1.user.dto.UserRequest
import com.sandbrick.sbp.api.v1.user.dto.UserResponse
import com.sandbrick.sbp.domain.User
import com.sandbrick.sbp.exception.DuplicateEntityException
import com.sandbrick.sbp.exception.ResourceNotFoundException
import com.sandbrick.sbp.repository.RoleRepository
import com.sandbrick.sbp.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun getAll(): List<UserResponse> =
        userRepository.findAll().map { it.toResponse() }

    fun getById(id: String): UserResponse =
        userRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("User with id $id not found") }
            .toResponse()

    @Transactional
    fun create(request: UserRequest): UserResponse {
        if (userRepository.existsByUsername(request.username)) {
            throw DuplicateEntityException("Username '${request.username}' already exists")
        }
        if (userRepository.existsByEmail(request.email)) {
            throw DuplicateEntityException("Email '${request.email}' already exists")
        }
        val roles = request.roles.map { roleName ->
            roleRepository.findByName(roleName)
                ?: throw ResourceNotFoundException("Role '$roleName' not found")
        }.toMutableSet()

        val user = User(
            username = request.username,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            roles = roles
        )
        return userRepository.save(user).toResponse()
    }

    @Transactional
    fun update(id: String, request: UserRequest): UserResponse {
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
        }.toMutableSet()

        user.username = request.username
        user.email = request.email
        user.password = passwordEncoder.encode(request.password)
        user.roles = roles

        return userRepository.save(user).toResponse()
    }

    @Transactional
    fun delete(id: String) {
        if (!userRepository.existsById(id)) {
            throw ResourceNotFoundException("User with id $id not found")
        }
        userRepository.deleteById(id)
    }

    @Transactional
    fun findByUsername(username: String): UserResponse {
        val user = userRepository.findByUsername(username)
            ?: throw ResourceNotFoundException("User with username '$username' not found")
        return user.toResponse()
    }

    private fun User.toResponse() = UserResponse(
        id = this.id.toString(),
        username = this.username,
        email = this.email,
        roles = this.roles.map { it.name }.toSet()
    )
}
