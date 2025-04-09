package com.sandbrick.sbp.service.role

import com.sandbrick.sbp.api.v1.role.dto.request.RoleRequest
import com.sandbrick.sbp.api.v1.role.dto.response.RoleResponse
import com.sandbrick.sbp.domain.role.Role
import com.sandbrick.sbp.exception.ResourceNotFoundException
import com.sandbrick.sbp.repository.RoleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoleService(
    private val roleRepository: RoleRepository
) {
    fun getAll(): List<RoleResponse> =
        roleRepository.findAll().map { it.toResponse() }

    fun getById(id: String): RoleResponse =
        roleRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Role with id $id not found") }
            .toResponse()

    @Transactional
    fun create(request: RoleRequest): RoleResponse {
        if (roleRepository.existsByName(request.name)) {
            throw IllegalArgumentException("Role with name '${request.name}' already exists")
        }
        val role = Role(name = request.name)
        return roleRepository.save(role).toResponse()
    }

    @Transactional
    fun update(id: String, request: RoleRequest): RoleResponse {
        val role = roleRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Role with id $id not found") }
        if (role.name != request.name && roleRepository.existsByName(request.name)) {
            throw IllegalArgumentException("Role with name '${request.name}' already exists")
        }
        role.name = request.name
        return roleRepository.save(role).toResponse()
    }

    @Transactional
    fun delete(id: String) {
        if (!roleRepository.existsById(id)) {
            throw ResourceNotFoundException("Role with id $id not found")
        }
        roleRepository.deleteById(id)
    }

    private fun Role.toResponse() = RoleResponse(
        id = this.id,
        name = this.name
    )
}
