package com.sasakirione.pokebuild.model.official.resource

import org.jetbrains.exposed.dao.id.IntIdTable

object Moves: IntIdTable("moves") {
    val name = varchar("name", 10).uniqueIndex()
    val type = reference("type", Types)
    val category = reference("category", MoveCategories)
    val generation = integer("generation")
}