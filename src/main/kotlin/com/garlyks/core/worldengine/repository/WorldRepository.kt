package com.garlyks.core.worldengine.repository

import com.garlyks.core.worldengine.domain.World
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WorldRepository : JpaRepository<World, String>
