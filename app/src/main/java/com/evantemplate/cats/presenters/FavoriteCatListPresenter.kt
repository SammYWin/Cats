package com.evantemplate.cats.presenters

import android.app.DownloadManager
import android.net.Uri
import android.os.Environment
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
import java.math.BigInteger
import java.security.MessageDigest

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

    fun downloadImage(manager: DownloadManager, imgUrl: String) {
        val url = imgUrl
        val request = DownloadManager.Request(Uri.parse(url))
        val extension = url.substring(url.lastIndexOf("."))

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle(url.md5())
        request.setDescription("cat image")

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            url.md5() + extension
        )
        manager.enqueue(request)
    }

    fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}