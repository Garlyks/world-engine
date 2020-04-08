package com.garlyks.core.worldengine.dto

import com.garlyks.core.worldengine.domain.Item
import java.util.UUID

data class ItemDTO(
        val id: String? = null,
        val name: String,
        val x: Int,
        val y: Int
) {
    fun toDomain(worldId: String): Item {
        return Item(
                id = this.id ?: UUID.randomUUID().toString(),
                name = this.name,
                position = Item.Position(
                        X = this.x,
                        Y = this.y),
                worldId = worldId)
    }
}
