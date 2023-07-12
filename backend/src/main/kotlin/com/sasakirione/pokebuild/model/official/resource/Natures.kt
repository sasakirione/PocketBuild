package com.sasakirione.pokebuild.model.official.resource

import org.jetbrains.exposed.dao.id.IntIdTable

object Natures: IntIdTable("natures") {
    val name = varchar("name", 10).uniqueIndex()
    val increase = integer("increase")
    val decrease = integer("decrease")
}