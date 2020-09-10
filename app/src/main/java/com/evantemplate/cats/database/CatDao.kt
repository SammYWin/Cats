package com.evantemplate.cats.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.evantemplate.cats.models.Cat
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface CatDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCat(cat: Cat): Completable

    @Delete
    fun deleteCat(cat: Cat): Completable

    @Query("SELECT * FROM favorite_cats_table ORDER BY id DESC")
    fun getAllFavoriteCats(): Single<List<Cat>>
}