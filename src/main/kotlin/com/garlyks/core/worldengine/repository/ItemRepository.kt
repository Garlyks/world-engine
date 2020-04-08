package com.garlyks.core.worldengine.repository

import com.garlyks.core.worldengine.domain.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : JpaRepository<Item, String> {

    @Query("""
        SELECT * FROM item 
        WHERE 
            item.world_id = :worldId 
            AND item.x >= :minXposition
            AND item.x <= :maxXposition
            AND item.y >= :minYposition
            AND item.y <= :maxYposition
    """,
            nativeQuery = true
    )
    fun findByArea(
            worldId: String,
            minXposition: Int,
            minYposition: Int,
            maxXposition: Int,
            maxYposition: Int
    ): List<Item>

}


