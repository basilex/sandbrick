package com.sandbrick.sbp.repository

import com.sandbrick.sbp.domain.role.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, String> {
    fun existsByName(name: String): Boolean
    fun findByName(name: String): Role?
}
