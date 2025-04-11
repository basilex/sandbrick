package com.sandbrick.sbp.config

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
class AppProperties {

    val validation: Validation = Validation()
    val token: Token = Token()

    class Validation {
        @Min(3)
        @Max(255)
        var firstNameMinLength: Int = 3

        @Min(3)
        @Max(255)
        var firstNameMaxLength: Int = 255

        @Min(3)
        @Max(255)
        var lastNameMinLength: Int = 3

        @Min(3)
        @Max(255)
        var lastNameMaxLength: Int = 255

        @Min(6)
        @Max(255)
        var passwordMinLength: Int = 6

        @Min(6)
        @Max(255)
        var passwordMaxLength: Int = 255
    }

    class Token {
        @Min(1)
        @Max(120)
        var accessMinutes: Long = 15

        @Min(1)
        @Max(30)
        var refreshDays: Long = 7
    }
}