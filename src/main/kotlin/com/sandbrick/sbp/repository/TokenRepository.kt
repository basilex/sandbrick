package com.sandbrick.sbp.repository

import com.sandbrick.sbp.domain.Token
import com.sandbrick.sbp.domain.User
import com.sandbrick.sbp.domain.auth.TokenType
import org.springframework.data.jpa.repository.JpaRepository
import java.time.Instant

interface TokenRepository : JpaRepository<Token, String> {
    fun findByToken(token: String): Token?
    fun findAllByUserAndType(user: User, type: TokenType): List<Token>
    fun deleteAllByUser(user: User)
    fun deleteByToken(token: String)
    fun deleteAllByExpiryDateBefore(before: Instant)
    fun findByTokenAndType(token: String, type: TokenType): Token?
}
