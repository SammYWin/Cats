package com.evantemplate.cats.database

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CatDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCat(cat: CatEntity): Completable

    @Delete
    fun deleteCat(cat: CatEntity): Completable

    @Query("SELECT * FROM favorite_cats_table ORDER BY id DESC")
    fun getAllFavoriteCats(): Single<List<CatEntity>>
}