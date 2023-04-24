package com.sasakirione.pokebuild.model.official.event

import com.sasakirione.pokebuild.model.official.resource.GoodCategories
import com.sasakirione.pokebuild.model.official.resource.Goods
import org.jetbrains.exposed.dao.id.IntIdTable

object GoodDetails: IntIdTable("good_details") {
    val goodId = reference("good_id", Goods)
    val description = text("description")
    val category = reference("category", GoodCategories)
    val generation = integer("generation")
}