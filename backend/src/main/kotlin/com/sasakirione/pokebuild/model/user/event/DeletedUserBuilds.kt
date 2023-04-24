package com.sasakirione.pokebuild.model.user.event

import com.sasakirione.pokebuild.model.user.resource.UserBuilds
import org.jetbrains.exposed.dao.id.IntIdTable

object DeletedUserBuilds: IntIdTable("deleted_user_builds") {
    val userBuilds = reference("user_builds", UserBuilds)
}