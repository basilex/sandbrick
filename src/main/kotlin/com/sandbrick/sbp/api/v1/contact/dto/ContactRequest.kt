package com.sandbrick.sbp.api.v1.contact.dto

import com.sandbrick.sbp.domain.contact.ContactType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Schema(description = "Payload for creating or updating a contact")
data class ContactRequest(

    @field:NotNull(message = "Contact type is required")
    @Schema(
        description = "Contact type (EMAIL, PHONE, TELEGRAM, etc.)",
        example = "EMAIL",
        required = true
    )
    val type: ContactType,

    @field:NotBlank(message = "Contact content must not be blank")
    @Schema(
        description = "Contact content such as email address, phone number, etc.",
        example = "john.doe@gmail.com",
        required = true
    )
    val content: String,

    @Schema(
        description = "Set to true if this is the preferred contact method",
        example = "true",
        defaultValue = "false"
    )
    val preferrable: Boolean = false
)
