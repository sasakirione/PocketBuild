package com.sasakirione.pokebuild.model.official.resource

import org.jetbrains.exposed.dao.id.IntIdTable

object Goods: IntIdTable("goods") {
    val name = varchar("name", 10).uniqueIndex()
}