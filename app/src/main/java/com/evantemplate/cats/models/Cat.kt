package com.evantemplate.cats.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "favorite_cats_table")
data class Cat(
    @PrimaryKey
    val id: String,
    @Json(name = "url")
    val imgUrl: String,
){
    var isInFavorites:Boolean = false
}