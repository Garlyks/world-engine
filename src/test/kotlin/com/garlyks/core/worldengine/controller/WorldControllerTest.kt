package com.garlyks.core.worldengine.controller

import com.garlyks.core.worldengine.dto.WorldDTO
import com.garlyks.core.worldengine.util.Fixtures
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.util.UUID

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class WorldControllerTest {

    @LocalServerPort
    private var port: Int = 0

    private val restTemplate: RestTemplate = RestTemplateBuilder().build()

    @Test
    fun getWorldRespondNotFound() {
        assertThrows(HttpClientErrorException.NotFound::class.java) {
            restTemplate.getForEntity("http://localhost:$port/api/v1/worlds/${UUID.randomUUID()}", WorldDTO::class.java) }
    }

    @Test
    fun postWorldRespondOk() {
        val world = Fixtures.world().toDTO()

        val responseCreate = restTemplate.postForEntity("http://localhost:$port/api/v1/worlds", world, WorldDTO::class.java)
        val responseGet = restTemplate.getForEntity(
                "http://localhost:$port/api/v1/worlds/${responseCreate.body!!.id}",
                WorldDTO::class.java)

        Assertions.assertThat(responseCreate.statusCodeValue).isEqualTo(201)
        Assertions.assertThat(responseGet.statusCodeValue).isEqualTo(200)
        Assertions.assertThat(world).isEqualToIgnoringGivenFields(responseGet.body, "id")
    }
}
