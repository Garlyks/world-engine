package com.garlyks.core.worldengine.util

import com.garlyks.core.worldengine.domain.Item
import com.garlyks.core.worldengine.domain.World
import java.util.Random
import java.util.UUID

object Fixtures {

    fun world(
            id: String? = null,
            width: Int? = null,
            height: Int? = null
    ): World {
        return World(
                id = id,
                height = width ?: Random().nextInt(1000),
                width = height ?: Random().nextInt(1000),
                name = "Dummy world ${id ?: UUID.randomUUID()}"
        )
    }

    fun item(
            id: String? = null,
            world: World? = null,
            x: Int? = null,
            y: Int? = null): Item {
        return Item(
                id = id,
                position = Item.Position(
                        X = x ?: Random().nextInt(world?.width ?: 1000),
                        Y = y ?: Random().nextInt(world?.height ?: 1000)),
                name = "Dummy Item ${UUID.randomUUID()}",
                worldId = world?.id
        )
    }
}
