package com.sasakirione.pokebuild.model.user.resource

import org.jetbrains.exposed.dao.id.IntIdTable

object Users: IntIdTable("users") {
    val authId = varchar("auth_id", 50)
}