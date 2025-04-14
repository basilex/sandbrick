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
    @Column(name = "type", nullable = false, length = 32)
    var type: TokenType,  // e.g. ACCESS or REFRESH

    @field:Size(min = 1, max = 512, message = "Token must be between 1 and 512 characters")
    @Column(name = "token", nullable = false, unique = true, length = 512)
    var token: String,

    @Column(name = "expired", nullable = false)
    var expired: Boolean = false,

    @Column(name = "revoked", nullable = false)
    var revoked: Boolean = false,

    @Column(name = "expiry_date", nullable = false)
    var expiryDate: Instant,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User
) : BaseAuditEntity()
