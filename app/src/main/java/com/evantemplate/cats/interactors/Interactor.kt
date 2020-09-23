package com.evantemplate.cats.interactors

import android.util.Log
import com.evantemplate.cats.models.Cat
import com.evantemplate.cats.repositories.CatsRepository
import com.evantemplate.cats.repositories.NetCatsRepository
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

class Interactor(
    private val netRepo: NetCatsRepository,
    private val dbRepo: CatsRepository,

) {
    val list = mutableListOf<Cat>()

    fun getAllCats() = netRepo.loadAllCats()
        .doOnNext {  list.addAll(it) }
        .map { list }

    fun addToFavorite(cat: Cat) = dbRepo.addToFavorites(cat)

    fun getFavoriteCats() = dbRepo.loadFavCats()
    fun deleteCatFromFav(cat: Cat) = dbRepo.deleteFromFavorites(cat)
}