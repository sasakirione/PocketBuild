package com.sasakirione.pokebuild.model.official.resource

import org.jetbrains.exposed.dao.id.IntIdTable

object MoveCategories: IntIdTable("move_categories") {
    val name = varchar("name", 10).uniqueIndex()
}