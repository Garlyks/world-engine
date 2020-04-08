package com.garlyks.core.worldengine.dto

import com.garlyks.core.worldengine.domain.World
import java.util.UUID

data class WorldDTO(
        val id: String? = null,
        val name: String,
        val width: Int,
        val height: Int
){
    fun toDomain(): World {
        return World(
                id = this.id ?: UUID.randomUUID().toString(),
                name = this.name,
                width = this.width,
                height = this.height
        )
    }
}


