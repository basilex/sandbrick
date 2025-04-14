package com.sandbrick.sbp.domain.auth

/**
 * Enum representing the type of JWT token.
 *
 * - ACCESS: Short-lived token used for authenticating API requests.
 * - REFRESH: Longer-lived token used to obtain new access tokens.
 */
enum class TokenType {
    ACCESS,
    REFRESH
}
