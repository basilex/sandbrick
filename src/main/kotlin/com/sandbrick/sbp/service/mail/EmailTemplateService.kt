package com.sandbrick.sbp.service.mail

import com.sandbrick.sbp.config.AppProperties
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class EmailTemplateService(
    private val templateEngine: TemplateEngine,
    private val appProperties: AppProperties
) {
    fun renderVerificationEmail(username: String, token: String): String {
        val context = Context().apply {
            setVariable("username", username)
            setVariable("verificationUrl", "${appProperties.url.frontendBase}/api/v1/auth/verify-email/confirm?token=$token")
        }
        return templateEngine.process("email/verification", context)
    }

    fun renderResetPasswordEmail(username: String, token: String): String {
        val context = Context().apply {
            setVariable("username", username)
            setVariable("resetUrl", "${appProperties.url.frontendBase}/api/v1/auth/reset-password?token=$token")
        }
        return templateEngine.process("email/reset-password", context)
    }
}
