package com.garlyks.core.worldengine.domain

import com.garlyks.core.worldengine.dto.WorldDTO
import org.hibernate.annotations.GenericGenerator
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class World(
        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        val id: String? = null,
        val name: String? = null,
        val width: Int? = null,
        val height: Int? = null
) {
    fun toDTO(): WorldDTO {
        return WorldDTO(
                id = this.id,
                name = this.name ?: "",
                width = this.width ?: 0,
                height = this.height ?: 0
        )
    }
}

