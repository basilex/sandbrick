package com.sandbrick.sbp.service

import com.sandbrick.sbp.api.v1.contact.dto.ContactRequest
import com.sandbrick.sbp.domain.Contact
import com.sandbrick.sbp.domain.contact.ContactType
import com.sandbrick.sbp.exception.ResourceNotFoundException
import com.sandbrick.sbp.mapper.ContactMapper
import com.sandbrick.sbp.repository.ContactRepository
import com.sandbrick.sbp.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ContactService(
    private val contactRepository: ContactRepository,
    private val userRepository: UserRepository,
    private val contactMapper: ContactMapper
) {
    fun getAll(): List<Contact> = contactRepository.findAll()

    fun getFiltered(
        page: Int,
        size: Int,
        userId: String?,
        type: ContactType?,
        preferrable: Boolean?
    ): Page<Contact> {
        val pageable = PageRequest.of(page, size, Sort.by("updatedAt").descending())
        return contactRepository.findByFilters(userId, type, preferrable, pageable)
    }

    fun getByUserId(userId: String): List<Contact> =
        contactRepository.findAllByUserId(userId)

    @Transactional
    fun create(userId: String, request: ContactRequest): Contact {
        val user = userRepository.findById(userId)
            .orElseThrow { ResourceNotFoundException("User with id $userId not found") }

        val contact = contactMapper.toEntity(user, request)
        return contactRepository.save(contact)
    }

    @Transactional
    fun update(id: String, request: ContactRequest): Contact {
        val contact = contactRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Contact with id $id not found") }
        contact.apply {
            type = request.type
            content = request.content
            preferrable = request.preferrable
        }
        return contactRepository.save(contact)
    }

    @Transactional
    fun delete(contactId: String) {
        if (!contactRepository.existsById(contactId)) {
            throw ResourceNotFoundException("Contact with id $contactId not found")
        }
        contactRepository.deleteById(contactId)
    }
}
