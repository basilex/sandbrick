package com.sandbrick.sbp.domain

import com.sandbrick.sbp.domain.base.BaseAuditEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "currency")
data class Currency(
    @field:NotBlank(message = "Code must not be empty")
    @field:Size(min = 3, max = 3, message = "Code must be exactly 3 characters")
    @Column(nullable = false, unique = true)
    var code: String, // e.g. "USD"

    @field:NotBlank(message = "Name must not be empty")
    @field:Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    @Column(nullable = false, unique = true)
    var name: String, // e.g. "US Dollar"

    @field:NotBlank(message = "Symbol must not be empty")
    @field:Size(min = 1, max = 5, message = "Symbol must be between 1 and 5 characters")
    @Column(nullable = false, length = 5)
    var symbol: String, // e.g. "$"
) : BaseAuditEntity()
