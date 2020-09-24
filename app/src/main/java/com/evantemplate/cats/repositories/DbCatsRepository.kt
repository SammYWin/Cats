package com.evantemplate.cats.repositories


import com.evantemplate.cats.database.CatDao
import com.evantemplate.cats.models.Cat
import com.evantemplate.cats.database.CatEntity
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject


interface CatsRepository {
    fun loadFavCats(): Single<List<Cat>>
    fun addToFavorites(cat: Cat): Completable
    fun deleteFromFavorites(cat: Cat): Completable
}

class DbCatsRepository @Inject constructor(
    val dataBase: CatDao
) : CatsRepository {
    override fun loadFavCats(): Single<List<Cat>> = dataBase.getAllFavoriteCats()
        .map { list -> list.map{ Cat(it.id, it.imgUrl, it.isInFavorites) } }

    override fun addToFavorites(cat: Cat) = dataBase.insertCat(CatEntity(cat.id, cat.imgUrl, cat.isInFavorites))

    override fun deleteFromFavorites(cat: Cat) = dataBase.deleteCat(CatEntity(cat.id, cat.imgUrl, cat.isInFavorites))
}