package com.sasakirione.pokebuild.model.official.event

import com.sasakirione.pokebuild.model.official.resource.Abilities
import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * # 特性の詳細を表すテーブル
 * 特性の詳細を管理するテーブル。特性の詳細仕様が変更した際に更新を行います。
 *
 * ## 使用例
 * - 第9世代でリベロ系がナーフされた事を表す場合はその新しい仕様(一度だけタイプが変わる旨)をdescriptionに記載し、generationには9を指定します。
 */
object AbilityDetails: IntIdTable("ability_details") {
    /**
     * 該当の特性のID
     */
    val abilityId = reference("ability_id", Abilities)
    /**
     * 該当の特性の説明
     */
    val description = text("description")
    /**
     * 該当の特性の説明に変わった初めての世代
     */
    val generation = integer("generation")
}