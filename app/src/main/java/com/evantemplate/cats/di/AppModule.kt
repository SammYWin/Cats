package com.evantemplate.cats.di

import android.app.Application
import android.content.Context
import com.evantemplate.cats.database.CatDao
import com.evantemplate.cats.database.CatDatabase
import com.evantemplate.cats.models.Cat
import com.evantemplate.cats.network.CatApi
import com.evantemplate.cats.repositories.CatsRepository
import com.evantemplate.cats.repositories.DbCatsRepository
import com.evantemplate.cats.repositories.NetCatsRepository
import dagger.Module
import dagger.Provides

@Module
class AppModule(
    val context: Context
) {

    @Provides
    fun provideDB(): CatDao = CatDatabase.getInstance(this.context).catDao

    @Provides
    fun provideDbCatsRepository(catDao: CatDao): CatsRepository = DbCatsRepository(catDao)

    @Provides
    fun provideNetCatRepo(): NetCatsRepository = NetCatsRepository(CatApi.retrofitService)
}