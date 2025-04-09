package com.sandbrick.sbp.domain.currency

import com.sandbrick.sbp.domain.base.BaseAuditEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.*

@Entity
@Table(name = "currency")
data class Currency(
    @Id val id: String = UUID.randomUUID().toString(),

    @field:NotBlank(message = "Code must not be empty")
    @field:Size(min = 3, max = 3, message = "Code must be exactly 3 characters")
    @Column(nullable = false, unique = true)
    val code: String, // e.g. "USD"

    @field:NotBlank(message = "Name must not be empty")
    @field:Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    @Column(nullable = false, unique = true)
    val name: String, // e.g. "US Dollar"

    @field:NotBlank(message = "Symbol must not be empty")
    @field:Size(min = 1, max = 5, message = "Symbol must be between 1 and 5 characters")
    @Column(nullable = false, length = 5)
    val symbol: String, // e.g. "$"
) : BaseAuditEntity()
