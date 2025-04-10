package com.sandbrick.sbp.domain.role

import com.sandbrick.sbp.domain.base.BaseAuditEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.*

@Entity
@Table(name = "role")
class Role(
    @Id
    val id: UUID = UUID.randomUUID(),

    @field:NotBlank(message = "Role name is required")
    @field:Size(min = 3, max = 64, message = "Name must be from 3 to 255 characters")
    @Column(nullable = false, unique = true)
    var name: String
) : BaseAuditEntity()
