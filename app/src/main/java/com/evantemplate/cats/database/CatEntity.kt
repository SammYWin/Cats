package com.evantemplate.cats.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

@Entity(tableName = "favorite_cats_table")
data class CatEntity(
    @PrimaryKey
    val id: String,
    val imgUrl: String,
){
    var isInFavorites:Boolean = false
}