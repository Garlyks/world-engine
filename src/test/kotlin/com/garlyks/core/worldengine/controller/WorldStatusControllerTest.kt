package com.garlyks.core.worldengine.controller

import com.garlyks.core.worldengine.dto.WorldStatusResponse
import com.garlyks.core.worldengine.repository.ItemRepository
import com.garlyks.core.worldengine.repository.WorldRepository
import com.garlyks.core.worldengine.util.Fixtures
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.util.UUID


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
internal class WorldStatusControllerTest {

    @LocalServerPort
    private var port: Int = 0

    private val restTemplate: RestTemplate = RestTemplateBuilder().build()

    @Autowired
    private lateinit var worldRepository: WorldRepository

    @Autowired
    private lateinit var itemRepository: ItemRepository

    @Test
    fun getWorldStatusRespondNotFound() {
        Assertions.assertThrows(HttpClientErrorException.NotFound::class.java) {
            restTemplate.getForEntity("http://localhost:$port/api/v1/worlds/${UUID.randomUUID()}/status", WorldStatusResponse::class.java)
        }
    }

    @Test
    fun getWorldStatusRespondOkWithDefaultValues() {
        val world = worldRepository.save(Fixtures.world(id = UUID.randomUUID().toString()))
        val items = itemRepository.saveAll(listOf(
                Fixtures.item(id = UUID.randomUUID().toString(), world = world),
                Fixtures.item(id = UUID.randomUUID().toString(), world = world),
                Fixtures.item(id = UUID.randomUUID().toString(), world = world),
                Fixtures.item(id = UUID.randomUUID().toString(), world = world)
        ))

        val response = restTemplate.getForEntity("http://localhost:$port/api/v1/worlds/${world.id}/status", WorldStatusResponse::class.java)
        assertThat(response.body!!.objects).containsExactlyInAnyOrderElementsOf(items.map { it.toDTO() })
    }

    @Test
    fun getWorldStatusRespondOkWithDefinedBoundaries() {
        val world = worldRepository.save(Fixtures.world(
                id = UUID.randomUUID().toString(),
                width = 10,
                height = 10))
        val items = itemRepository.saveAll(listOf(
                Fixtures.item(id = UUID.randomUUID().toString(), world = world, x = 1, y = 1),
                Fixtures.item(id = UUID.randomUUID().toString(), world = world, x = 2, y = 1),
                Fixtures.item(id = UUID.randomUUID().toString(), world = world, x = 1, y = 3),
                Fixtures.item(id = UUID.randomUUID().toString(), world = world, x = 3, y = 1)
        ))

        val url = UriComponentsBuilder.fromHttpUrl("http://localhost:$port/api/v1/worlds/${world.id}/status")
                .queryParam("x", 0)
                .queryParam("y", 0)
                .queryParam("width", 2)
                .queryParam("height", 2)
                .toUriString()

        val response = restTemplate.getForEntity(url, WorldStatusResponse::class.java)
        assertThat(response.body!!.objects).containsExactlyInAnyOrderElementsOf(items.take(2) .map { it.toDTO() })
    }
}
