package com.sandbrick.sbp.service.mail

import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

class MailServiceTest {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Test
    fun `fake mail service logs output`() {
        val fakeMailService = object : MailService {
            override fun send(to: String, subject: String, body: String) {
                logger.info("Test Mail: to=$to, subject=$subject")
            }
        }

        fakeMailService.send("test@example.com", "Test Subject", "Hello")
        // add asserts?
    }
}
