package com.sandbrick.sbp.service.role

import com.sandbrick.sbp.api.v1.role.dto.request.RoleRequest
import com.sandbrick.sbp.domain.Role
import com.sandbrick.sbp.exception.ResourceNotFoundException
import com.sandbrick.sbp.repository.RoleRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoleService(
    private val roleRepository: RoleRepository
) {
    fun getAll(): List<Role> =
        roleRepository.findAll(Sort.by("name").ascending())

    fun getAllPaged(page: Int, size: Int): Page<Role> {
        val pageable = PageRequest.of(page, size, Sort.by("name").ascending())
        return roleRepository.findAll(pageable)
    }

    fun getById(id: String): Role =
        roleRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Role with id $id not found") }

    @Transactional
    fun create(request: RoleRequest): Role {
        if (roleRepository.existsByName(request.name)) {
            throw IllegalArgumentException("Role with name '${request.name}' already exists")
        }
        val role = Role(name = request.name)
        return roleRepository.save(role)
    }

    @Transactional
    fun update(id: String, request: RoleRequest): Role {
        val role = roleRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Role with id $id not found") }

        if (role.name != request.name && roleRepository.existsByName(request.name)) {
            throw IllegalArgumentException("Role with name '${request.name}' already exists")
        }

        role.name = request.name
        return roleRepository.save(role)
    }

    @Transactional
    fun delete(id: String) {
        if (!roleRepository.existsById(id)) {
            throw ResourceNotFoundException("Role with id $id not found")
        }
        roleRepository.deleteById(id)
    }
}
