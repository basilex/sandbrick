package com.sandbrick.sbp.domain.auth

import com.sandbrick.sbp.domain.User
import com.sandbrick.sbp.domain.base.BaseAuditEntity
import com.sandbrick.sbp.util.Xid
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import java.time.Instant

@Schema(description = "Entity representing an email verification token associated with a user.")
@Entity
@Table(name = "email_verification_token")
class EVToken(

    @Schema(
        description = "Unique verification token string",
        example = "a1b2c3d4e5f6g7h8i9j0k1l2"
    )
    @Column(nullable = false, unique = true, length = 255)
    var token: String = Xid.generate(),

    @Schema(
        description = "Date and time when the token expires (in UTC, ISO 8601 format)",
        example = "2025-05-01T12:00:00Z"
    )
    @Column(name = "expires_at", nullable = false)
    var expiryDate: Instant,

    @Schema(
        description = "Flag indicating whether the token has been confirmed",
        example = "false"
    )
    @Column(nullable = false)
    var confirmed: Boolean = false,

    @Schema(description = "User to whom the token belongs")
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.MERGE])
    @JoinColumn(name = "user_id", nullable = false)
    var user: User
) : BaseAuditEntity()
