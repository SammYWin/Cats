package com.evantemplate.cats.repositories

import com.evantemplate.cats.models.Cat
import com.evantemplate.cats.network.CatApi
import com.evantemplate.cats.network.CatApiService
import io.reactivex.Flowable
import javax.inject.Inject

class NetCatsRepository @Inject constructor(val service: CatApiService) {

    fun loadAllCats(): Flowable<List<Cat>> = service.getCats("20","1", "Desc")
}