package com.sandbrick.sbp.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.thymeleaf.TemplateEngine
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import org.thymeleaf.templatemode.TemplateMode

@Configuration
class MailTemplateConfig {

    @Bean
    fun htmlTemplateResolver(): ClassLoaderTemplateResolver {
        return ClassLoaderTemplateResolver().apply {
            suffix = ".html"
            prefix = "templates/"
            templateMode = TemplateMode.HTML
            characterEncoding = "UTF-8"
            order = 1
        }
    }

    @Bean
    fun thymeleafTemplateEngine(): TemplateEngine {
        return TemplateEngine().apply {
            setTemplateResolver(htmlTemplateResolver())
        }
    }
}
