package com.sasakirione.pokebuild.model.user.event

import com.sasakirione.pokebuild.model.user.resource.UserBuilds
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object UserBuildDetails: IntIdTable("user_build_details") {
    val userBuildId = reference("user_build_id", UserBuilds)
    val buildName = varchar("build_name", 64)
    val buildDetail = text("build_detail")
    val series = integer("series")
    val generation = integer("generation")
    val createdAt = datetime("created_at")
}