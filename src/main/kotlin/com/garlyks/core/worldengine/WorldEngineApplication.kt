package com.garlyks.core.worldengine

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WorldEngineApplication

fun main(args: Array<String>) {
	runApplication<WorldEngineApplication>(*args)
}
