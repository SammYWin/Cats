package com.evantemplate.cats.presenters

import android.util.Log
import com.evantemplate.cats.interactors.Interactor
import com.evantemplate.cats.models.Cat
import com.evantemplate.cats.ui.FavoriteCatListView
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class FavoriteCatListPresenter(val interactor: Interactor): MvpPresenter<FavoriteCatListView>() {
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        loadFavoriteCats()
    }

    private fun loadFavoriteCats() {
        interactor.getFavoriteCats()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {catList ->
                    viewState.updateFavCatList(catList)},
                {e -> Log.d("M_CatListViewModel", "$e")}
            ).let {
                compositeDisposable.add(it)
            }
    }

    fun deleteCatFromFav(cat: Cat) {
        interactor.deleteCatFromFav(cat)
            .toSingle { true }
            .flatMap {
                interactor.getFavoriteCats()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {catList -> viewState.updateFavCatList(catList)},
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