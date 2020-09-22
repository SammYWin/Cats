package com.evantemplate.cats.presenters

import android.util.Log
import com.evantemplate.cats.ui.CatListView
import com.evantemplate.cats.interactors.Interactor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class CatListPresenter(val interactor: Interactor) : MvpPresenter<CatListView>() {
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        loadMoreCats()
    }

    fun loadMoreCats() {
        interactor.getAllCats()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { catList ->
                    viewState.showAllCats(catList)
                },
                { e ->
                    Log.d("M_CatListViewModel", "$e")
                }
            )
    }
}