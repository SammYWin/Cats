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
    context: Context
) {

    private val database: CatDao = CatDatabase.getInstance(context).catDao

    @Provides
    fun provideDbCatsRepository(): CatsRepository = DbCatsRepository(database)

    @Provides
    fun provideNetCatRepo(): NetCatsRepository = NetCatsRepository(CatApi.retrofitService)
}