package com.sasakirione.pokebuild.model.official.event

import com.sasakirione.pokebuild.model.official.resource.GoodCategories
import com.sasakirione.pokebuild.model.official.resource.Goods
import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * # 道具詳細のマッピングテーブル
 * 道具の詳細を管理します。
 *
 * ## ルール
 * - 実装時の道具の説明の場合はgenerationに道具自体の登場した世代を指定してください。
 * - 変更された道具の説明の場合はその道具が変更された世代を指定してください。
 */
object GoodDetails: IntIdTable("good_details") {
    /**
     * 道具ID
     */
    val goodId = reference("good_id", Goods)
    /**
     * 説明
     */
    val description = text("description")
    /**
     * カテゴリ
     */
    val category = reference("category", GoodCategories)
    /**
     * 世代(道具が実装された世代、もしくは道具が変更された世代)
     */
    val generation = integer("generation")
}