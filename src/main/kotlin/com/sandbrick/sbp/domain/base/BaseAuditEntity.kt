
package com.sandbrick.sbp.domain.base

import com.sandbrick.sbp.util.Xid
import jakarta.persistence.*
import java.time.Instant

@MappedSuperclass
abstract class BaseAuditEntity {
    @Id
    val id: String = Xid.generate()

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
