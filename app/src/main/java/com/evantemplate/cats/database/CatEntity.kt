package com.evantemplate.cats.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "favorite_cats_table")
data class CatEntity(
    @PrimaryKey
    val id: String,
    @Json(name = "url")
    val url: String,
){
    var isInFavorites:Boolean = false
}