package com.sandbrick.sbp.repository

import com.sandbrick.sbp.domain.auth.ResetToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
interface ResetTokenRepository : JpaRepository<ResetToken, String> {
    fun findByToken(token: String): ResetToken?
    fun findAllByUserId(userId: String): List<ResetToken>
    fun deleteAllByUserId(userId: String)
    fun existsByToken(token: String): Boolean
    fun deleteAllByExpiryDateBefore(before: Instant)
    fun deleteExpiredTokens(before: Instant = Instant.now())

    fun findByTokenAndUsedIsFalseAndExpiryDateAfter(token: String, now: Instant): ResetToken?
}
