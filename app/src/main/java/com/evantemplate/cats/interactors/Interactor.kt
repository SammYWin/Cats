package com.evantemplate.cats.interactors

import android.util.Log
import com.evantemplate.cats.models.Cat
import com.evantemplate.cats.repositories.CatsRepository
import com.evantemplate.cats.repositories.NetCatsRepository
import io.reactivex.schedulers.Schedulers

class Interactor(
    val netRepo: NetCatsRepository,
    val dbRepo: CatsRepository
) {
    fun getAllCats() = netRepo.loadAllCats()
}