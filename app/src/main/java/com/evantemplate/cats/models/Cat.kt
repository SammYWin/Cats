package com.evantemplate.cats.models

import com.squareup.moshi.Json

data class Cat(
    val id: String,
    @Json(name = "url")
    val imgUrl: String,
    var isInFavorites:Boolean = false
){
}