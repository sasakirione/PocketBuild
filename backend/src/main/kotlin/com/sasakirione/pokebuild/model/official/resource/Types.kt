package com.sasakirione.pokebuild.model.official.resource

import org.jetbrains.exposed.dao.id.IntIdTable

object Types: IntIdTable("types") {
    val name = varchar("name", 10).uniqueIndex()
    val color = varchar("color", 10)
    val generation = integer("generation")
}