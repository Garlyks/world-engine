package com.garlyks.core.worldengine.domain

import com.garlyks.core.worldengine.dto.ItemDTO
import org.hibernate.annotations.GenericGenerator
import javax.persistence.Embeddable
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Item(
        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        val id: String? = null,
        val worldId: String? = null,
        val name: String? = null,
        @Embedded
        val position: Position = Position(0,0)) {

    @Embeddable
    data class Position(var X: Int? = null, var Y: Int? = null)

    fun toDTO(): ItemDTO {
        return ItemDTO(
                id = this.id,
                name = this.name ?: "",
                x = this.position.X ?: 0,
                y = this.position.Y ?: 0)
    }
}
