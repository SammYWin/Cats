package com.evantemplate.cats.presenters

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat
import com.evantemplate.cats.extensions.md5
import com.evantemplate.cats.ui.CatListView
import com.evantemplate.cats.interactors.Interactor
import com.evantemplate.cats.models.Cat
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import javax.inject.Inject

@InjectViewState
class CatListPresenter @Inject constructor(
    val interactor: Interactor
) : MvpPresenter<CatListView>() {
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
        interactor.deleteCatFromFav(cat)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {},
                {e -> Log.d("M_CatListViewModel", "$e")}
            ).let {
                compositeDisposable.add(it)
            }
    }

    fun downloadImage(imgUrl: String) {
        interactor.downloadImage(imgUrl)
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}