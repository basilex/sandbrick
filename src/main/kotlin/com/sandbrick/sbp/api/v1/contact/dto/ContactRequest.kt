package com.sandbrick.sbp.api.v1.contact.dto

import com.sandbrick.sbp.domain.contact.ContactType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Schema(description = "Request payload for creating or updating a contact")
data class ContactRequest(
    @field:NotNull
    @Schema(
        description = "Type of contact (e.g., EMAIL, PHONE, TELEGRAM, etc.)",
        example = "EMAIL",
        required = true
    )
    val type: ContactType,

    @field:NotBlank
    @Schema(
        description = "Content of the contact (email address, phone number, etc.)",
        example = "john.doe@gmail.com",
        required = true
    )
    val content: String,

    @Schema(
        description = "Indicates whether this is the preferred contact",
        example = "true",
        defaultValue = "false"
    )
    val preferrable: Boolean = false
)
