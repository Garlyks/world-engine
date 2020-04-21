package com.garlyks.core.worldengine.controller

import com.garlyks.core.worldengine.dto.WorldDTO
import com.garlyks.core.worldengine.service.WorldService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/v1/worlds")
@CrossOrigin
class WorldController(
        private val worldService: WorldService
) {

    @GetMapping
    fun getAllWorldStatus(): ResponseEntity<List<WorldDTO>> {
        return ResponseEntity.ok(worldService.getAllWorlds().map { world -> world.toDTO() })
    }

    @GetMapping("{id}")
    fun getWorldStatus(
            @PathVariable id: String
    ): ResponseEntity<WorldDTO> {
        return ResponseEntity.ok(worldService.getWorld(id).toDTO())
    }

    @PostMapping
    fun createWorldStatus(@RequestBody worldDTO: WorldDTO): ResponseEntity<WorldDTO> {

        val world = worldService.createWorld(worldDTO.toDomain()).toDTO()
        val location: URI = URI.create(java.lang.String.format("/persons/%s", worldDTO.id))

        return ResponseEntity.created(location).body(world)
    }
}
