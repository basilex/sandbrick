package com.sandbrick.sbp.api.v1.contact

import com.sandbrick.sbp.api.v1.contact.dto.ContactRequest
import com.sandbrick.sbp.api.v1.contact.dto.ContactResponse
import com.sandbrick.sbp.domain.contact.ContactType
import com.sandbrick.sbp.mapper.ContactMapper
import com.sandbrick.sbp.service.ContactService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize

@RestController
@RequestMapping("/api/v1/contacts")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Contact", description = "Contact management operations")
class ContactController(
    private val contactService: ContactService,
    private val contactMapper: ContactMapper
) {

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all contacts (no pagination)")
    fun getAll(): List<ContactResponse> =
        contactService.getAll().map(contactMapper::toResponse)

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get paginated contacts with optional filters")
    fun getFiltered(
        @Parameter(description = "Page number", example = "0")
        @RequestParam(defaultValue = "0") page: Int,
        @Parameter(description = "Page size", example = "10")
        @RequestParam(defaultValue = "10") size: Int,
        @Parameter(description = "Filter by user ID", example = "c7f0e0a1e9r0vlu2")
        @RequestParam(required = false) userId: String?,
        @Parameter(description = "Filter by contact type", example = "EMAIL")
        @RequestParam(required = false) type: ContactType?,
        @Parameter(description = "Filter by preferred status", example = "true")
        @RequestParam(required = false) preferrable: Boolean?
    ): Page<ContactResponse> =
        contactService.getFiltered(page, size, userId, type, preferrable).map(contactMapper::toResponse)

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all contacts for specific user")
    fun getByUserId(@PathVariable userId: String): List<ContactResponse> =
        contactService.getByUserId(userId).map(contactMapper::toResponse)

    @PostMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new contact for user")
    fun create(
        @PathVariable userId: String,
        @RequestBody @Valid request: ContactRequest
    ): ContactResponse =
        contactMapper.toResponse(contactService.create(userId, request))

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @contactSecurity.isOwner(#id, authentication.name)")
    @Operation(
        summary = "Update contact by ID",
        description = "Allows an admin or the contact's owner to update contact information",
        responses = [
            ApiResponse(responseCode = "200", description = "Contact updated successfully"),
            ApiResponse(responseCode = "403", description = "Forbidden"),
            ApiResponse(responseCode = "404", description = "Contact not found")
        ]
    )
    fun update(
        @PathVariable id: String,
        @RequestBody @Valid request: ContactRequest
    ): ContactResponse =
        contactService.update(id, request).let(contactMapper::toResponse)

    @DeleteMapping("/{contactId}")
    @PreAuthorize("hasRole('ADMIN') or @contactSecurity.isOwner(#contactId, authentication.name)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Delete a contact by ID",
        description = "Allows an admin or the contact's owner to delete a contact",
        responses = [
            ApiResponse(responseCode = "204", description = "Contact deleted successfully"),
            ApiResponse(responseCode = "403", description = "Forbidden"),
            ApiResponse(responseCode = "404", description = "Contact not found")
        ]
    )
    fun delete(@PathVariable contactId: String) =
        contactService.delete(contactId)
}
