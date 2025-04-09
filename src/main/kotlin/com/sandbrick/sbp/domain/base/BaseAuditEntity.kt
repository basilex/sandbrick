
package com.sandbrick.sbp.domain.base

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.time.Instant

@MappedSuperclass
abstract class BaseAuditEntity {
    @Column(nullable = false, updatable = false)
    var createdAt: Instant = Instant.now()

    @Column(nullable = false)
    var updatedAt: Instant = Instant.now()

    @PrePersist
    fun prePersist() {
        val now = Instant.now()
        createdAt = now
        updatedAt = now
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = Instant.now()
    }
}
