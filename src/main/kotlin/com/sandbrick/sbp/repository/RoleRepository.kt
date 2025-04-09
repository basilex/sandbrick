package com.sandbrick.sbp.domain.role

import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, String> {
    fun existsByName(name: String): Boolean
}
