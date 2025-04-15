package com.sandbrick.sbp.service.mail.impl

import com.sandbrick.sbp.service.mail.MailService
import jakarta.mail.*
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service
import java.util.Properties

@Service
@ConditionalOnProperty(prefix = "mail.smtp", name = ["enabled"], havingValue = "true")
class SmtpMailService(
    @Value("\${mail.smtp.host}") private val host: String,
    @Value("\${mail.smtp.port}") private val port: Int,
    @Value("\${mail.smtp.username}") private val username: String,
    @Value("\${mail.smtp.password}") private val password: String,
    @Value("\${mail.smtp.from}") private val from: String
) : MailService {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun send(to: String, subject: String, body: String) {
        val props = Properties().apply {
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
            put("mail.smtp.host", host)
            put("mail.smtp.port", port.toString())
        }

        val session = Session.getInstance(props, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(username, password)
            }
        })

        try {
            val message = MimeMessage(session).apply {
                setFrom(InternetAddress(this@SmtpMailService.from))
                setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false))
                setSubject(subject)
                setContent(body, "text/html; charset=utf-8")
            }

            val transport = session.getTransport("smtp")

            transport.connect(host, port, username, password)
            transport.sendMessage(message, message.allRecipients)
            transport.close()

            logger.info("Email sent to $to with subject \"$subject\"")
        } catch (ex: MessagingException) {
            logger.error("Failed to send email to $to: ${ex.message}", ex)
        }

    }

}
