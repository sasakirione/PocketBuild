package com.sasakirione.pokebuild.model.official.resource

import org.jetbrains.exposed.dao.id.IntIdTable

object Tags: IntIdTable("tags") {
    val name = varchar("name", 15)
    val color = varchar("color", 50)
}