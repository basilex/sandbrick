package com.sandbrick.sbp.domain

import com.sandbrick.sbp.domain.base.BaseAuditEntity
import com.sandbrick.sbp.domain.contact.ContactType
import jakarta.persistence.*
import jakarta.validation.constraints.Size

@Entity
@Table(name = "contact")
data class Contact(
    @field:Size(max = 32, message = "Contact type must be max = 32 characters")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var type: ContactType,

    @field:Size(max = 255, message = "Contact content must be max = 255 characters")
    @Column(columnDefinition = "text", nullable = false)
    var content: String,

    @Column(nullable = false)
    var preferrable: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User
) : BaseAuditEntity() {
    val userId: String get() = user.id
}
