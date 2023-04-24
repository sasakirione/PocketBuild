package com.sasakirione.pokebuild.model.user.resource

import org.jetbrains.exposed.dao.id.IntIdTable

object Users: IntIdTable("users") {
    val userId = varchar("user_id", 50)
}