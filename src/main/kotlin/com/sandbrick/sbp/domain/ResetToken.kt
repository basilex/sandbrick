package com.sandbrick.sbp.domain.auth

import com.sandbrick.sbp.domain.User
import com.sandbrick.sbp.domain.base.BaseAuditEntity
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import java.time.Instant

@Schema(description = "Reset password token entity")
@Entity
@Table(name = "reset_token")
class ResetToken(

    @Schema(description = "Reset token value", example = "a1b2c3d4e5f6g7h8i9j0")
    @Column(nullable = false, unique = true, length = 255)
    var token: String,

    @Schema(description = "Expiration timestamp of the token", example = "2025-05-01T12:00:00Z")
    @Column(name = "expires_at", nullable = false)
    var expiryDate: Instant,

    @Schema(description = "Whether the token has already been used", example = "false")
    @Column(nullable = false)
    var used: Boolean = false,

    @Schema(description = "The user to whom the token belongs")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User
) : BaseAuditEntity()
