package com.sandbrick.sbp.repository

import com.sandbrick.sbp.domain.Token
import com.sandbrick.sbp.domain.User
import com.sandbrick.sbp.domain.auth.TokenType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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
    fun findByExpiryDateAfter(expiryDate: Instant, pageable: Pageable): Page<Token>
    fun findByTokenAndType(token: String, type: TokenType): Token?

    @Query("""
    SELECT t FROM Token t 
     WHERE t.user.id = :userId AND t.expired = false AND t.revoked = false
    """)
    fun findAllValidTokensByUser(@Param("userId") userId: String): List<Token>

    @Query("""
    SELECT t FROM Token t
     WHERE t.expiryDate > :now
       AND (:revoked IS NULL OR t.revoked = :revoked)
       AND (:type IS NULL OR t.type = :type)
     """)
    fun findActiveFiltered(
        @Param("now") now: Instant,
        @Param("revoked") revoked: Boolean?,
        @Param("type") type: TokenType?,
        pageable: Pageable
    ): Page<Token>
}
