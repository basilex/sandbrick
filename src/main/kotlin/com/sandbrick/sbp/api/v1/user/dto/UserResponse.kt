package com.sandbrick.sbp.api.v1.user.dto

data class UserResponse(
    val id: String,
    val username: String,
    val email: String,
    val roles: Set<String>
)
