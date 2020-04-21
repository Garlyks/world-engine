package com.garlyks.core.worldengine.controller

import com.garlyks.core.worldengine.dto.ItemDTO
import com.garlyks.core.worldengine.service.ItemService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/v1/worlds/{worldId}/items")
@CrossOrigin
class ItemController(
        private val itemService: ItemService
) {

    @GetMapping("{id}")
    fun getItem(
            @PathVariable id: String
    ): ResponseEntity<ItemDTO> {
        return ResponseEntity.ok(itemService.getItem(id).toDTO())
    }

    @PostMapping
    fun createItem(@RequestBody itemDTO: ItemDTO, @PathVariable worldId: String): ResponseEntity<ItemDTO> {

        val item = itemService.createItem(itemDTO.toDomain(worldId)).toDTO()
        val location: URI = URI.create(java.lang.String.format("/persons/%s", itemDTO.id))

        return ResponseEntity.created(location).body(item)
    }

    @DeleteMapping
    fun deleteAllItems(@PathVariable worldId: String): ResponseEntity<Any> {
        itemService.deleteAllItems(worldId)
        return ResponseEntity.accepted().build()
    }

}
