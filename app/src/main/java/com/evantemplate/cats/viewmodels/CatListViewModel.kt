package com.evantemplate.cats.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.evantemplate.cats.models.Cat
import com.evantemplate.cats.network.CatApi
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CatListViewModel: ViewModel() {

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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}