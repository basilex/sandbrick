package com.sandbrick.sbp.mapper

import com.sandbrick.sbp.api.v1.token.dto.TokenResponse
import com.sandbrick.sbp.domain.Token
import org.springframework.stereotype.Component

@Component
class TokenMapper {
    fun toResponse(token: Token): TokenResponse = TokenResponse(
        id = token.id,
        token = token.token,
        type = token.type,
        expired = token.expired,
        revoked = token.revoked,
        expiryDate = token.expiryDate,
        userId = token.user.id
    )
}
