package com.sandbrick.sbp.service

import com.sandbrick.sbp.api.v1.auth.dto.EmailVerificationConfirmRequest
import com.sandbrick.sbp.api.v1.auth.dto.EmailVerificationRequest
import com.sandbrick.sbp.domain.auth.EmailVerificationToken
import com.sandbrick.sbp.domain.contact.ContactType
import com.sandbrick.sbp.exception.ResourceNotFoundException
import com.sandbrick.sbp.exception.ValidationException
import com.sandbrick.sbp.repository.ContactRepository
import com.sandbrick.sbp.repository.EmailVerificationTokenRepository
import com.sandbrick.sbp.service.mail.EmailTemplateService
import com.sandbrick.sbp.service.mail.MailService
import com.sandbrick.sbp.util.Xid
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class EmailVerificationService(
    private val contactRepository: ContactRepository,
    private val emailVerificationTokenRepository: EmailVerificationTokenRepository,
    private val emailTemplateService: EmailTemplateService,
    private val mailService: MailService
) {

    @Transactional
    fun createVerificationToken(request: EmailVerificationRequest): String {
        val contact = contactRepository.findByContentAndType(request.email, ContactType.EMAIL.name)
            ?: throw ResourceNotFoundException("Email not found in user contacts")

        val user = contact.user
        val token = Xid.generate()
        val expiry = Instant.now().plus(1, ChronoUnit.HOURS)

        emailVerificationTokenRepository.save(
            EmailVerificationToken(token = token, expiryDate = expiry, user = user)
        )

        val html = emailTemplateService.renderVerificationEmail(user.username, token)

        mailService.send(
            to = request.email,
            subject = "Verify your Sandbrick account",
            body = html
        )
        return token
    }

    @Transactional
    fun confirmVerification(request: EmailVerificationConfirmRequest) {
        val token = emailVerificationTokenRepository.findByTokenAndConfirmedIsFalseAndExpiryDateAfter(
            request.token, Instant.now()
        ) ?: throw ValidationException("Invalid or expired verification token")

        token.confirmed = true
        token.user.emailVerified = true

        emailVerificationTokenRepository.save(token)
    }

    @Transactional
    fun cleanExpiredTokens(before: Instant = Instant.now()) {
        emailVerificationTokenRepository.deleteAllByExpiryDateBefore(before)
    }
}
