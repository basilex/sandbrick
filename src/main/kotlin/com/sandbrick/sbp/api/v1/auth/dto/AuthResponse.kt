package com.sandbrick.sbp.api.v1.auth.dto

data class AuthResponse(
    val accessToken: String,
    val tokenType: String = "Bearer"
)