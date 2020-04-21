package com.garlyks.core.worldengine.service


import com.garlyks.core.worldengine.domain.Item
import com.garlyks.core.worldengine.exception.InvalidParameterException
import com.garlyks.core.worldengine.exception.ResourceNotFoundException
import com.garlyks.core.worldengine.repository.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ItemService(
        private val itemRepository: ItemRepository,
        private val worldService: WorldService) {
    fun createItem(item: Item): Item {
        validate(item)
        return itemRepository.save(item)
    }

    private fun validate(item: Item) {
        val world = worldService.getWorld(item.worldId!!)
        if (item.position.X!! > world.width!!) {
            throw InvalidParameterException("X position out of world boundaries")
        }
        if (item.position.Y!! > world.height!!) {
            throw InvalidParameterException("Y position out of world boundaries")
        }
    }

    fun getItem(id: String): Item {
        return itemRepository.findById(id).orElseThrow{ResourceNotFoundException("Item $id not found")}
    }

    @Transactional
    fun deleteAllItems(worldId:String) {
         itemRepository.deleteAllByWorldId(worldId)
    }


}
