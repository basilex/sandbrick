package com.sandbrick.sbp.api.v1.contact.dto

import com.sandbrick.sbp.domain.contact.ContactType
import io.swagger.v3.oas.annotations.media.Schema
import java.time.Instant

@Schema(description = "Response model representing a user's contact information")
data class ContactResponse(

    @Schema(
        description = "Unique identifier for the contact",
        example = "c7f0e0a1e9r0abc1"
    )
    val id: String,

    @Schema(
        description = "ID of the user associated with this contact",
        example = "c7f0e0a1e9r0vlu2"
    )
    val userId: String,

    @Schema(
        description = "Type of contact (EMAIL, PHONE, TELEGRAM, etc.)",
        example = "EMAIL"
    )
    val type: ContactType,

    @Schema(
        description = "The actual contact content (e.g. phone number, email address)",
        example = "john.doe@gmail.com"
    )
    val content: String,

    @Schema(
        description = "Whether this is the user's preferred contact method",
        example = "true"
    )
    val preferrable: Boolean,

    @Schema(
        description = "Date and time when the contact was created",
        example = "2025-04-11T12:00:00Z"
    )
    val createdAt: Instant,

    @Schema(
        description = "Date and time when the contact was last updated",
        example = "2025-04-11T12:30:00Z"
    )
    val updatedAt: Instant
)
