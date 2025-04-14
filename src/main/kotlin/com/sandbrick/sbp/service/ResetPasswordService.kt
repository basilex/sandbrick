package com.sandbrick.sbp.service

import com.sandbrick.sbp.api.v1.auth.dto.ResetPasswordRequest
import com.sandbrick.sbp.api.v1.auth.dto.ResetPasswordConfirmRequest
import com.sandbrick.sbp.domain.auth.ResetToken
import com.sandbrick.sbp.exception.ResourceNotFoundException
import com.sandbrick.sbp.exception.ValidationException
import com.sandbrick.sbp.repository.ResetTokenRepository
import com.sandbrick.sbp.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class ResetPasswordService(
    private val resetTokenRepository: ResetTokenRepository,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun generateResetToken(request: ResetPasswordRequest): String {
        val contactEmail = request.email.lowercase().trim()

        val user = userRepository.findAll().firstOrNull { user ->
            user.contacts.any {
                it.type.name == "EMAIL" && it.content.equals(contactEmail, ignoreCase = true)
            }
        } ?: throw ResourceNotFoundException("User with email ${request.email} not found")

        resetTokenRepository.deleteAllByUserId(user.id)

        val tokenValue = UUID.randomUUID().toString().replace("-", "")
        val expiry = Instant.now().plusSeconds(60 * 60) // 1 hour validity

        val token = ResetToken(
            token = tokenValue,
            expiryDate = expiry,
            user = user
        )
        resetTokenRepository.save(token)
        return tokenValue
    }

    @Transactional
    fun confirmReset(request: ResetPasswordConfirmRequest) {
        val token = resetTokenRepository
            .findByTokenAndUsedIsFalseAndExpiryDateAfter(request.token, Instant.now())
            ?: throw ValidationException("Invalid or expired token")

        val user = token.user
        user.password = passwordEncoder.encode(request.newPassword)
        token.used = true

        resetTokenRepository.save(token)
        userRepository.save(user)
    }
}
