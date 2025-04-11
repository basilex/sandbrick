package com.sandbrick.sbp.service

import com.sandbrick.sbp.domain.Token
import com.sandbrick.sbp.domain.User
import com.sandbrick.sbp.domain.auth.TokenType
import com.sandbrick.sbp.exception.ResourceNotFoundException
import com.sandbrick.sbp.repository.TokenRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class TokenService(
    private val tokenRepository: TokenRepository
) {
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

    @Transactional
    fun revokeAllUserTokens(user: User) {
        val validTokens = tokenRepository.findAllValidTokensByUser(user.id)
        validTokens.forEach {
            it.expired = true
            it.revoked = true
        }
        tokenRepository.saveAll(validTokens)
    }

    @Transactional
    fun saveToken(user: User, tokenValue: String, type: TokenType, expiry: Instant): Token {
        val token = Token(
            type = type,
            token = tokenValue,
            expiryDate = expiry,
            user = user
        )
        return tokenRepository.save(token)
    }

    // === CRUD additions for ADMIN ===

    fun getAll(): List<Token> = tokenRepository.findAll()

    fun getAllPaged(page: Int, size: Int): Page<Token> {
        val pageable = PageRequest.of(page, size, Sort.by("expiryDate").descending())
        return tokenRepository.findAll(pageable)
    }

    fun getActiveTokensPaged(page: Int, size: Int): Page<Token> {
        val pageable = PageRequest.of(page, size, Sort.by("expiryDate").descending())
        val now = Instant.now()
        return tokenRepository.findByExpiryDateAfter(now, pageable)
    }

    fun getValidTokensByUser(userId: String): List<Token> =
        tokenRepository.findAllValidTokensByUser(userId)

    fun deleteById(id: String) {
        if (!tokenRepository.existsById(id)) {
            throw ResourceNotFoundException("Token with id $id not found")
        }
        tokenRepository.deleteById(id)
    }
}
