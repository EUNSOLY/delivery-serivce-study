package org.delivery.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["org.delivery"])
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}