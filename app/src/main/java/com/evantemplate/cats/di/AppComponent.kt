package com.evantemplate.cats.di

import com.evantemplate.cats.ui.CatListFragment
import com.evantemplate.cats.ui.FavoriteCatsListFragment
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(fragment: CatListFragment)
    fun inject(fragment: FavoriteCatsListFragment)
}