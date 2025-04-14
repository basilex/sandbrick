package com.sandbrick.sbp.domain

import com.sandbrick.sbp.domain.base.BaseAuditEntity
import jakarta.persistence.*
import jakarta.validation.constraints.Size

@Entity
@Table(name = "profile")
class Profile(

    @field:Size(max = 255, message = "First name must be up to 255 characters")
    @Column(name = "first_name", length = 255)
    var firstName: String? = null,

    @field:Size(max = 255, message = "Last name must be up to 255 characters")
    @Column(name = "last_name", length = 255)
    var lastName: String? = null,

    @field:Size(max = 2048, message = "Avatar URL must be up to 2048 characters")
    @Column(name = "avatar_url", length = 2048)
    var avatarUrl: String? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    var user: User
) : BaseAuditEntity() {
    val userId: String get() = user.id
}
