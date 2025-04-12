package com.sandbrick.sbp.security

import com.sandbrick.sbp.repository.ContactRepository
import org.springframework.stereotype.Component

@Component
class SecurityService(
    private val contactRepository: ContactRepository
) {
    fun isOwner(contactId: String, username: String): Boolean =
        contactRepository.findById(contactId)
            .map { it.user.username == username }
            .orElse(false)
}