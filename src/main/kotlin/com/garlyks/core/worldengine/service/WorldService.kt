package com.garlyks.core.worldengine.service


import com.garlyks.core.worldengine.domain.World
import com.garlyks.core.worldengine.exception.ResourceNotFoundException
import com.garlyks.core.worldengine.repository.WorldRepository
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.stereotype.Service

@Service
class WorldService(private val worldRepository: WorldRepository) {

    fun getAllWorlds(): List<World> {
        return worldRepository.findAll()
    }

    fun createWorld(world: World): World {
        return worldRepository.save(world)
    }

    fun getWorld(id: String): World {
        return worldRepository.findById(id).orElseThrow{ ResourceNotFoundException("World $id not found")}
    }
}
