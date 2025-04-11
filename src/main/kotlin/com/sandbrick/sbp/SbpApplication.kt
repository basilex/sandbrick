package com.sandbrick.sbp

import com.sandbrick.sbp.config.AppProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(
	value = [AppProperties::class]
)
class SbpApplication

fun main(args: Array<String>) {
	runApplication<SbpApplication>(*args)
}
