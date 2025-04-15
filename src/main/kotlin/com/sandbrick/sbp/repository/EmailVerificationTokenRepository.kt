package com.sandbrick.sbp.repository

import com.sandbrick.sbp.domain.auth.EmailVerificationToken
import org.springframework.data.jpa.repository.JpaRepository
import java.time.Instant

interface EmailVerificationTokenRepository : JpaRepository<EmailVerificationToken, String> {
    fun findByTokenAndConfirmedIsFalseAndExpiryDateAfter(token: String, now: Instant): EmailVerificationToken?
    fun deleteAllByExpiryDateBefore(before: Instant)
}
