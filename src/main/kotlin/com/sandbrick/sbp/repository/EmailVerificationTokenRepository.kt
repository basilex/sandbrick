package com.sandbrick.sbp.repository

import com.sandbrick.sbp.domain.auth.EVToken
import org.springframework.data.jpa.repository.JpaRepository
import java.time.Instant

interface EmailVerificationTokenRepository : JpaRepository<EVToken, String> {
    fun findByTokenAndConfirmedIsFalseAndExpiryDateAfter(token: String, now: Instant): EVToken?
    fun deleteAllByExpiryDateBefore(before: Instant)
}
