package com.sandbrick.sbp.domain.contact

import io.swagger.v3.oas.annotations.media.Schema

enum class ContactType {
    @Schema(description = "Email address")
    EMAIL,

    @Schema(description = "Phone number")
    PHONE,

    @Schema(description = "Telegram handle or link")
    TELEGRAM,

    @Schema(description = "WhatsApp contact")
    WHATSAPP,

    @Schema(description = "Viber contact")
    VIBER,

    @Schema(description = "Signal contact")
    SIGNAL,

    @Schema(description = "Other contact type")
    OTHER
}
