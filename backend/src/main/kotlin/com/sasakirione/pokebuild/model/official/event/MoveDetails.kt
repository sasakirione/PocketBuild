package com.sasakirione.pokebuild.model.official.event

import com.sasakirione.pokebuild.model.official.resource.Moves
import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * # 技詳細のテーブル
 * 技の詳細を管理します。
 *
 * ## ルール
 * - 実装時の技の説明の場合はgenerationに技自体の登場した世代を指定してください。
 * - 変更された技の説明の場合はその技が変更された世代を指定してください。
 */
object MoveDetails: IntIdTable("move_details") {
    /**
     * 技ID
     */
    val moveId = reference("move_id", Moves)
    /**
     * 説明
     */
    val description = text("description")
    /**
     * 世代(技が実装された世代、もしくは技が変更された世代)
     */
    val generation = integer("generation")
}