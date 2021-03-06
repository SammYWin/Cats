package com.evantemplate.cats.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.evantemplate.cats.database.CatDao
import com.evantemplate.cats.models.Cat
import com.evantemplate.cats.network.CatApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CatListViewModel(val dataBase: CatDao): ViewModel() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    val mutableCatList = mutableListOf<Cat>()

    private val catList = MutableLiveData<List<Cat>>()
    fun getCats(): LiveData<List<Cat>> = catList

    init {
        loadCats()
    }

    fun loadCats(){
        compositeDisposable.add(CatApi.retrofitService.getCats("20","1", "Desc")
            .toObservable()
            .subscribeOn(Schedulers.io())
            .subscribe(
                { cats ->
                    mutableCatList.addAll(cats)
                    catList.postValue(mutableCatList) },
                {e -> Log.d("M_CatListViewModel", "$e")}
            )
        )
    }

    fun addToFavorites(cat: Cat){
        dataBase.insertCat(cat)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {Log.d("M_CatListViewModel", "Cat successfully inserted!")},
                {e -> Log.d("M_CatListViewModel", "Error while inserting cat: $e")}
            ).let {
                compositeDisposable.add(it)
            }
    }

    fun deleteFromFavorites(cat: Cat){
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