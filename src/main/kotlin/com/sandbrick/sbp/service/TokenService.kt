package com.sandbrick.sbp.service

import com.sandbrick.sbp.domain.Token
import com.sandbrick.sbp.domain.User
import com.sandbrick.sbp.domain.auth.TokenType
import com.sandbrick.sbp.repository.TokenRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class TokenService(
    private val tokenRepository: TokenRepository
) {
    fun saveToken(user: User, tokenValue: String, type: TokenType, expiry: Instant): Token {
        val token = Token(
            type = type,
            token = tokenValue,
            expiryDate = expiry,
            user = user
        )
        return tokenRepository.save(token)
    }

    @Transactional
    fun findByToken(token: String): Token? =
        tokenRepository.findByToken(token)

    @Transactional
    fun deleteToken(token: String) =
        tokenRepository.deleteByToken(token)

    @Transactional
    fun deleteTokensByUser(user: User) =
        tokenRepository.deleteAllByUser(user)

    @Transactional
    fun deleteExpiredTokens(before: Instant = Instant.now()) =
        tokenRepository.deleteAllByExpiryDateBefore(before)

    @Transactional
    fun isRefreshTokenValid(token: String): Boolean {
        val dbToken = tokenRepository.findByToken(token) ?: return false
        return dbToken.type == TokenType.REFRESH && dbToken.expiryDate.isAfter(Instant.now())
    }

    @Transactional
    fun deleteAllUserTokens(user: User) {
        tokenRepository.deleteAllByUser(user)
    }
}
