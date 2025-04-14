package com.sandbrick.sbp.domain

import com.sandbrick.sbp.domain.base.BaseAuditEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "role")
class Role(

    @field:NotBlank(message = "Role name is required")
    @field:Size(min = 3, max = 64, message = "Role name must be between 3 and 64 characters")
    @Column(name = "name", nullable = false, unique = true, length = 64)
    var name: String
) : BaseAuditEntity()
