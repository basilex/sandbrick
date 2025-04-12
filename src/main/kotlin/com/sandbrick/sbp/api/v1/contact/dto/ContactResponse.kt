package com.sandbrick.sbp.api.v1.contact.dto

import com.sandbrick.sbp.domain.contact.ContactType
import io.swagger.v3.oas.annotations.media.Schema
import java.time.Instant

data class ContactResponse(
    @Schema(description = "Unique contact identifier", example = "c7f0e0a1e9r0abc1")
    val id: String,

    @Schema(description = "Associated user ID", example = "c7f0e0a1e9r0vlu2")
    val userId: String,

    @Schema(
        description = "Type of contact (EMAIL, PHONE, TELEGRAM, etc.)",
        example = "EMAIL"
    )
    val type: ContactType,

    @Schema(description = "Contact content", example = "john.doe@gmail.com")
    val content: String,

    @Schema(description = "Is this the preferred contact method", example = "true")
    val preferrable: Boolean,

    @Schema(description = "Timestamp when the contact was created", example = "2025-04-11T12:00:00Z")
    val createdAt: Instant,

    @Schema(description = "Timestamp of last update", example = "2025-04-11T12:30:00Z")
    val updatedAt: Instant
)
