package com.sandbrick.sbp.service.mail

interface MailService {
    fun send(to: String, subject: String, body: String)
}
