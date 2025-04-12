package com.sandbrick.sbp.mapper

import com.sandbrick.sbp.api.v1.contact.dto.ContactRequest
import com.sandbrick.sbp.api.v1.contact.dto.ContactResponse
import com.sandbrick.sbp.domain.Contact
import com.sandbrick.sbp.domain.User
import org.springframework.stereotype.Component

@Component
class ContactMapper {
    fun toResponse(contact: Contact): ContactResponse =
        ContactResponse(
            id = contact.id,
            userId = contact.user.id,
            type = contact.type,
            content = contact.content,
            preferrable = contact.preferrable,
            createdAt = contact.createdAt,
            updatedAt = contact.updatedAt
        )

    fun toEntity(user: User, request: ContactRequest): Contact =
        Contact(
            user = user,
            type = request.type,
            content = request.content,
            preferrable = request.preferrable
        )
}
