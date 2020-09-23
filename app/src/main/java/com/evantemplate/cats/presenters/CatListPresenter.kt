package com.evantemplate.cats.presenters

import android.util.Log
import com.evantemplate.cats.ui.CatListView
import com.evantemplate.cats.interactors.Interactor
import com.evantemplate.cats.models.Cat
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class CatListPresenter(val interactor: Interactor) : MvpPresenter<CatListView>() {
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        loadCats()
    }

    fun loadCats() {
        interactor.getAllCats()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { catList -> viewState.showAllCats(catList) },
                { e -> Log.d("M_CatListViewModel", "$e") }
            ).let {
                compositeDisposable.add(it)
            }
    }

    fun addToFavoritesBtnPressed(cat: Cat) {
        interactor.addToFavorite(cat)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {},
                { e -> Log.d("M_CatListPresenter", "$e")}
            ).let {
                compositeDisposable.add(it)
            }
    }

    fun deleteFromFavorite(cat: Cat) {
        //TODO not really working the way it should, need fix
        interactor.deleteCatFromFav(cat)
            .toSingle { true }
            .flatMap {
                interactor.getFavoriteCats()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {catList -> viewState.showAllCats(catList)},
                {e -> Log.d("M_CatListViewModel", "$e")}
            ).let {
                compositeDisposable.add(it)
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}