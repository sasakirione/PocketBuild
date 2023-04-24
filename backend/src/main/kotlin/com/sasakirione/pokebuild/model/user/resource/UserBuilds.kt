package com.sasakirione.pokebuild.model.user.resource

import org.jetbrains.exposed.dao.id.IntIdTable

object UserBuilds: IntIdTable("user_builds") {
    val userId = reference("user_id", Users)
}