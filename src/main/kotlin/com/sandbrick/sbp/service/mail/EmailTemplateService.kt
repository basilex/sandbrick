package com.sandbrick.sbp.service.mail

import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class EmailTemplateService(
    private val templateEngine: TemplateEngine
) {
    fun renderVerificationEmail(username: String, token: String): String {
        val context = Context().apply {
            setVariable("username", username)
            setVariable("verificationUrl", "http://localhost:8081/api/v1/auth/verify-email/confirm?token=$token")
        }
        return templateEngine.process("email/verification", context)
    }
}
