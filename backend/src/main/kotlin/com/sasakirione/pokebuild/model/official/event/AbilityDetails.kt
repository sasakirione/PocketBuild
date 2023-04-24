package com.sasakirione.pokebuild.model.official.event

import com.sasakirione.pokebuild.model.official.resource.Abilities
import org.jetbrains.exposed.dao.id.IntIdTable

object AbilityDetails: IntIdTable("ability_details") {
    val abilityId = reference("ability_id", Abilities)
    val description = text("description")
    val generation = integer("generation")
}