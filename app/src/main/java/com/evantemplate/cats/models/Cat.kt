package com.evantemplate.cats.models

data class Cat(
    val id: String,
    val url: String,
    var isInFavorites:Boolean = false
){
}