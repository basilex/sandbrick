package com.sandbrick.sbp.domain

import com.sandbrick.sbp.domain.base.BaseAuditEntity
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "users")
class User(
    @field:NotBlank(message = "Username is required")
    @field:Size(min = 3, max = 64, message = "Ussername must be from 3 to 64 characters")
    @Column(nullable = false, unique = true)
    var username: String,

    @field:Email(message = "Email should be valid")
    @field:NotBlank(message = "Email is required")
    @field:Size(min = 4, max = 255, message = "Email must be from 4 to 255 characters")
    @Column(nullable = false, unique = true)
    var email: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 6, max = 255, message = "Paassword must be from 6 to 255 characters")
    @Column(nullable = false)
    var password: String,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: MutableSet<Role> = mutableSetOf(),

    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    var profile: Profile? = null
) : BaseAuditEntity()
