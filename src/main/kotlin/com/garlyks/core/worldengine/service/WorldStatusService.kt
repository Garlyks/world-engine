package com.garlyks.core.worldengine.service

import com.garlyks.core.worldengine.domain.Item
import com.garlyks.core.worldengine.repository.ItemRepository
import org.springframework.stereotype.Service

@Service
class WorldStatusService(
        private val itemRepository: ItemRepository,
        private val worldService: WorldService
) {
    fun getWorldStatus(
            worldId: String,
            x: Int,
            y: Int,
            width: Int? = null,
            height: Int? = null
    ): List<Item> {
        val world = worldService.getWorld(worldId)

        return itemRepository.findByArea(
                worldId = worldId,
                minXposition = x,
                minYposition = y,
                maxXposition = width?.let { x.plus(it) } ?: world.width!!,
                maxYposition = height?.let{ y.plus(height)} ?: world.height!!
        )
    }
}
