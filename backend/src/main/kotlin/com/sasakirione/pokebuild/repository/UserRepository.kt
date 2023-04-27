package com.sasakirione.pokebuild.repository

import com.sasakirione.pokebuild.model.user.resource.Users
import org.jetbrains.exposed.sql.select

class UserRepository {
    /**
     * ユーザーIDを取得する
     *
     * @param authId 認証ID
     * @return ユーザーID
     */
    fun getUserId(authId: String): Int? =
        Users.select { Users.authId eq authId }.firstOrNull()?.let {
            return it[Users.id].value
        }
}