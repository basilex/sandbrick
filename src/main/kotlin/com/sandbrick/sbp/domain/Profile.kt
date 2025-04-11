package com.sandbrick.sbp.domain

import com.sandbrick.sbp.domain.base.BaseAuditEntity
import jakarta.persistence.*
import jakarta.validation.constraints.Size

@Entity
@Table(name = "profile")
class Profile(
    @field:Size(max = 255, message = "Firstname must be from 3 to 255 characters")
    @Column(nullable = true)
    var firstName: String? = null,

    @field:Size(max = 255, message = "Lastname must be from 3 to 255 characters")
    @Column(nullable = true)
    var lastName: String? = null,

    @field:Size(max = 32, message = "Phone must be from 3 to 32 characters")
    @Column(nullable = true)
    var phone: String? = null,

    @field:Size(max = 2048, message = "AvatarUrl must be from 3 to 255 characters")
    @Column(nullable = true)
    var avatarUrl: String? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    var user: User
) : BaseAuditEntity() {
    val userId: String get() = user.id
}