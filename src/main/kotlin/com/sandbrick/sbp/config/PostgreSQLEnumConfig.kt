package com.sandbrick.sbp.config

import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration

@Configuration
class PostgreSQLEnumConfig {

    @PostConstruct
    fun registerEnumTypes() {
        System.setProperty("hibernate.type.allow_enums_as_sql_enums", "true")
    }
}
