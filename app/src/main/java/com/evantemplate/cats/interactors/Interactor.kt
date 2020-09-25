package com.evantemplate.cats.interactors

import android.app.DownloadManager
import android.net.Uri
import android.os.Environment
import com.evantemplate.cats.extensions.md5
import com.evantemplate.cats.models.Cat
import com.evantemplate.cats.repositories.CatsRepository
import com.evantemplate.cats.repositories.NetCatsRepository
import javax.inject.Inject

class Interactor @Inject constructor(
    private val netRepo: NetCatsRepository,
    private val dbRepo: CatsRepository,
    private val downloadManager: DownloadManager
) {
    val catList = mutableListOf<Cat>()

    fun getAllCats() = netRepo.loadAllCats()
        .doOnNext {  catList.addAll(it) }
        .map { catList }

    fun addToFavorite(cat: Cat) = dbRepo.addToFavorites(Cat(cat.id, cat.imgUrl, isInFavorites = true))

    fun getFavoriteCats() = dbRepo.loadFavCats()

    fun deleteCatFromFav(cat: Cat) = dbRepo.deleteFromFavorites(cat)

    fun downloadImage(imgUrl: String){
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

        downloadManager.enqueue(request)
    }
}