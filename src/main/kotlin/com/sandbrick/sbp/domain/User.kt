package com.sandbrick.sbp.domain

import com.sandbrick.sbp.domain.auth.EmailVerificationToken
import com.sandbrick.sbp.domain.auth.ResetToken
import com.sandbrick.sbp.domain.base.BaseAuditEntity
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "users")
class User(

    @field:NotBlank(message = "Username is required")
    @field:Size(min = 3, max = 64, message = "Username must be between 3 and 64 characters")
    @Column(nullable = false, unique = true, length = 64)
    var username: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 6, max = 255, message = "Password must be between 6 and 255 characters")
    @Column(nullable = false, length = 255)
    var password: String,

    @Schema(description = "Whether the user's email has been verified", example = "true")
    @Column(name = "email_verified", nullable = false)
    var emailVerified: Boolean = false,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: MutableSet<Role> = mutableSetOf(),

    @OneToOne(
        mappedBy = "user",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    var profile: Profile? = null,

    @OneToMany(
        mappedBy = "user",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    var contacts: MutableSet<Contact> = mutableSetOf(),

    @OneToMany(
        mappedBy = "user",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    val resetTokens: MutableSet<ResetToken> = mutableSetOf(),

    @OneToMany(
        mappedBy = "user",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    var emailVerificationTokens: MutableSet<EmailVerificationToken> = mutableSetOf()

) : BaseAuditEntity()
