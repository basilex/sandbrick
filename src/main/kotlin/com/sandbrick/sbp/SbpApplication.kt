package com.sandbrick.sbp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SbpApplication

fun main(args: Array<String>) {
	runApplication<SbpApplication>(*args)
}
