package com.evantemplate.cats.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.evantemplate.cats.database.CatDao
import com.evantemplate.cats.models.Cat
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FavCatListViewModel(val dataBase: CatDao): ViewModel() {
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val catList = MutableLiveData<List<Cat>>()
    fun getCats(): LiveData<List<Cat>> = catList

    init {
        loadFavCats()
    }

    private fun loadFavCats() {
        dataBase.getAllFavoriteCats()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {favCatList ->
                 val newList = favCatList
                    newList.map { it.isInFavorites = true }
                    catList.postValue(newList)},
                {e -> Log.d("M_FavCatListViewModel", "Error retrieving all cats: $e")}
            ).let {
                compositeDisposable.add(it)
            }
    }

    fun deleteFromFavorites(cat: Cat){
        val newList = catList.value!!.toMutableList()
        newList.remove(cat)
        catList.value = newList

        dataBase.deleteCat(cat)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {Log.d("M_CatListViewModel", "Cat successfully deleted!")},
                {e -> Log.d("M_CatListViewModel", "Error while deletin cat: $e")}
            ).let {
                compositeDisposable.add(it)
            }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}