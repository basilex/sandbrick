package com.sandbrick.sbp.repository

import com.sandbrick.sbp.domain.Token
import com.sandbrick.sbp.domain.User
import com.sandbrick.sbp.domain.auth.TokenType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.Instant

interface TokenRepository : JpaRepository<Token, String> {
    fun findByToken(token: String): Token?
    fun findAllByUserAndType(user: User, type: TokenType): List<Token>
    fun deleteAllByUser(user: User)
    fun deleteByToken(token: String)
    fun deleteAllByExpiryDateBefore(before: Instant)
    fun findByTokenAndType(token: String, type: TokenType): Token?

    @Query("""
    SELECT t FROM Token t 
     WHERE t.user.id = :userId AND t.expired = false AND t.revoked = false
    """)
    fun findAllValidTokensByUser(@Param("userId") userId: String): List<Token>
}
