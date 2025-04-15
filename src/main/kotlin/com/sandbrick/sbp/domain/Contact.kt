package com.sandbrick.sbp.domain

import com.sandbrick.sbp.domain.base.BaseAuditEntity
import com.sandbrick.sbp.domain.contact.ContactType
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "contact")
data class Contact(

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32, columnDefinition = "contact_type")
    var type: ContactType,

    @field:NotBlank(message = "Contact content must not be blank")
    @field:Size(max = 255, message = "Contact content must not exceed 255 characters")
    @Column(nullable = false, columnDefinition = "text")
    var content: String,

    @Column(nullable = false)
    var preferrable: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User
) : BaseAuditEntity() {

    /**
     * Exposes user ID without forcing full user load.
     */
    val userId: String
        get() = user.id
}
