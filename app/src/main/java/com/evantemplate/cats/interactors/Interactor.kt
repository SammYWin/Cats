package com.evantemplate.cats.interactors

import com.evantemplate.cats.models.Cat
import com.evantemplate.cats.repositories.CatsRepository
import com.evantemplate.cats.repositories.NetCatsRepository

class Interactor(
    private val netRepo: NetCatsRepository,
    private val dbRepo: CatsRepository,

) {
    val catList = mutableListOf<Cat>()

    fun getAllCats() = netRepo.loadAllCats()
        .doOnNext {  catList.addAll(it) }
        .map { catList }

    fun addToFavorite(cat: Cat) = dbRepo.addToFavorites(Cat(cat.id, cat.imgUrl, isInFavorites = true))

    fun getFavoriteCats() = dbRepo.loadFavCats()
    fun deleteCatFromFav(cat: Cat) = dbRepo.deleteFromFavorites(cat)
}