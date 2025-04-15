package com.sandbrick.sbp.service.mail.impl

import com.sandbrick.sbp.service.mail.MailService
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Primary
@Profile("test")
class FakeMailService : MailService {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun send(to: String, subject: String, body: String) {
        logger.info("[FAKE MAIL] To: $to | Subject: $subject | Body: $body")
    }
}
