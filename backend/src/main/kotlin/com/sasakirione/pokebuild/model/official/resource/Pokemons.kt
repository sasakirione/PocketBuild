package com.sasakirione.pokebuild.model.official.resource

import org.jetbrains.exposed.dao.id.IntIdTable

object Pokemons : IntIdTable("pokemons") {
    val dexNo = integer("dex_no")
    val formNo = integer("form_no").default(1)
    val name = varchar("name", 10)
    val formName = varchar("form_name", 10).nullable()
    val generation = integer("generation")
}