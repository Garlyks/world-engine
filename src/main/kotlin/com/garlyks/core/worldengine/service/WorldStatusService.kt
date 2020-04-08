package com.garlyks.core.worldengine.service

import com.garlyks.core.worldengine.domain.Item
import com.garlyks.core.worldengine.exception.ResourceNotFoundException
import com.garlyks.core.worldengine.repository.ItemRepository
import com.garlyks.core.worldengine.repository.WorldRepository
import org.springframework.stereotype.Service

@Service
class WorldStatusService(
        private val itemRepository: ItemRepository,
        private val worldService: WorldService
) {
    fun getWorldStatus(
            worldId: String,
            minXposition: Int?,
            minYposition: Int?,
            maxXposition: Int? = null,
            maxYposition: Int? = null
    ): List<Item> {
        val world = worldService.getWorld(worldId)

        return itemRepository.findByArea(
                worldId = worldId,
                minXposition = minXposition ?: 0,
                minYposition = minYposition ?: 0,
                maxXposition = maxXposition ?: world.width!!,
                maxYposition = maxYposition ?: world.height!!
        )
    }
}
