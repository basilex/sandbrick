package com.sandbrick.sbp.domain

import com.sandbrick.sbp.domain.base.BaseAuditEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "role")
class Role(
    @field:NotBlank(message = "Role name is required")
    @field:Size(min = 3, max = 64, message = "Name must be from 3 to 64 characters")
    @Column(nullable = false, unique = true)
    var name: String
) : BaseAuditEntity()
