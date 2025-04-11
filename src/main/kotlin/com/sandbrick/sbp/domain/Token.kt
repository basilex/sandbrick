package com.sandbrick.sbp.domain

import com.sandbrick.sbp.domain.auth.TokenType
import com.sandbrick.sbp.domain.base.BaseAuditEntity
import jakarta.persistence.*
import jakarta.validation.constraints.Size
import java.time.Instant

@Entity
@Table(name = "token")
class Token(
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var type: TokenType,  // <-- access / refresh

    @field:Size(max = 512, message = "Token must be from 1 to 255 characters")
    @Column(nullable = false, unique = true)
    var token: String,

    @Column(nullable = false)
    var expired: Boolean = false,

    @Column(nullable = false)
    var revoked: Boolean = false,

    @Column(nullable = false)
    var expiryDate: Instant,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User
) : BaseAuditEntity()