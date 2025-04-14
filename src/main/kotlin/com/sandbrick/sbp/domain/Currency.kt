package com.sandbrick.sbp.domain

import com.sandbrick.sbp.domain.base.BaseAuditEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "currency")
data class Currency(

    @field:NotBlank(message = "Code must not be empty")
    @field:Size(min = 3, max = 3, message = "Code must be exactly 3 characters")
    @Column(nullable = false, unique = true, length = 3)
    var code: String, // Example: "USD"

    @field:NotBlank(message = "Name must not be empty")
    @field:Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    @Column(nullable = false, unique = true, length = 255)
    var name: String, // Example: "US Dollar"

    @field:NotBlank(message = "Symbol must not be empty")
    @field:Size(min = 1, max = 5, message = "Symbol must be between 1 and 5 characters")
    @Column(nullable = false, length = 5)
    var symbol: String // Example: "$"
) : BaseAuditEntity()
