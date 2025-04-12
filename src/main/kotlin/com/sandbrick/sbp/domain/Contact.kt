package com.sandbrick.sbp.domain

import com.sandbrick.sbp.domain.contact.ContactType
import com.sandbrick.sbp.util.Xid
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
@Table(name = "contact")
data class Contact(
    @Id
    var id: String = Xid.generate(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var type: ContactType,

    @Column(columnDefinition = "text", nullable = false)
    var content: String,

    @Column(nullable = false)
    var preferrable: Boolean = false,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    val updatedAt: Instant = Instant.now()
)
