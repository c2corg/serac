package org.camptocamp.serac

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SeracApplication

fun main(args: Array<String>) {
    runApplication<SeracApplication>(*args)
}
