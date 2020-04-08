package com.garlyks.core.worldengine.controller


import com.garlyks.core.worldengine.dto.ItemDTO
import com.garlyks.core.worldengine.repository.WorldRepository
import com.garlyks.core.worldengine.util.Fixtures
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.util.UUID

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class ItemControllerTest {

    @LocalServerPort
    private var port: Int = 0

    private val restTemplate: RestTemplate = RestTemplateBuilder().build()

    @Autowired
    private lateinit var worldRepository: WorldRepository

    @Test
    fun getItemRespondNotFound() {
        assertThrows(HttpClientErrorException.NotFound::class.java) {
            restTemplate.getForEntity("http://localhost:$port/api/v1/items/${UUID.randomUUID()}", ItemDTO::class.java)
        }
    }

    @Test
    fun postItemRespondOkWithDefaultValues() {
        val world = worldRepository.save(Fixtures.world(id = UUID.randomUUID().toString()))
        val item = Fixtures.item(world = world).toDTO()

        val responseCreate = restTemplate.postForEntity("http://localhost:$port/api/v1/worlds/${world.id}/items", item, ItemDTO::class.java)
        val responseGet = restTemplate.getForEntity(
                "http://localhost:$port/api/v1/worlds/${world.id}/items/${responseCreate.body!!.id}",
                ItemDTO::class.java)

        assertThat(responseCreate.statusCodeValue).isEqualTo(201)
        assertThat(responseGet.statusCodeValue).isEqualTo(200)
        assertThat(item).isEqualToIgnoringGivenFields(responseGet.body, "id")
    }

    @Test
    fun `should fail creating object outside of world boundaries X`() {
        val world = worldRepository.save(Fixtures.world(id = UUID.randomUUID().toString()))
        val item = Fixtures.item(
                world = world,
                x = world.width?.plus(1)
        ).toDTO()

        assertThrows(HttpClientErrorException.BadRequest::class.java) {
            restTemplate.postForEntity("http://localhost:$port/api/v1/worlds/${world.id}/items", item, ItemDTO::class.java)
        }
    }

    @Test
    fun `should fail creating object outside of world boundaries Y`() {
        val world = worldRepository.save(Fixtures.world(id = UUID.randomUUID().toString()))
        val item = Fixtures.item(
                world = world,
                y = world.height?.plus(1)
        ).toDTO()

        assertThrows(HttpClientErrorException.BadRequest::class.java) {
            restTemplate.postForEntity("http://localhost:$port/api/v1/worlds/${world.id}/items", item, ItemDTO::class.java)
        }
    }
}
