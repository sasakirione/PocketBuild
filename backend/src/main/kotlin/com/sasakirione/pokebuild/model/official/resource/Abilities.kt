package com.sasakirione.pokebuild.model.official.resource

import org.jetbrains.exposed.dao.id.IntIdTable

object Abilities: IntIdTable("abilities") {
    val name = varchar("name", 10).uniqueIndex()
    val generation = integer("generation")
}