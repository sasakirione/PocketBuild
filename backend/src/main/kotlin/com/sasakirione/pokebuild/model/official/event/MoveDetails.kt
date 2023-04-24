package com.sasakirione.pokebuild.model.official.event

import com.sasakirione.pokebuild.model.official.resource.Moves
import org.jetbrains.exposed.dao.id.IntIdTable

object MoveDetails: IntIdTable("move_details") {
    val moveId = reference("move_id", Moves)
    val description = text("description")
    val generation = integer("generation")
}