package com.garlyks.core.worldengine.controller

import com.garlyks.core.worldengine.dto.WorldStatusResponse
import com.garlyks.core.worldengine.service.WorldStatusService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/worlds/{worldId}/status")
class WorldStatusController(
        private val worldStatusService: WorldStatusService
){

    @GetMapping
    @CrossOrigin
    fun getWorldStatus(
            @PathVariable worldId: String,
            @RequestParam x: Int? = 0,
            @RequestParam y: Int? = 0,
            @RequestParam width: Int? = null,
            @RequestParam height: Int? = null
    ): WorldStatusResponse {
        return WorldStatusResponse(objects = worldStatusService.getWorldStatus(
                worldId = worldId,
                minXposition = x,
                minYposition = y,
                maxXposition = width,
                maxYposition = height
        ).map { item -> item.toDTO() })
    }

}
