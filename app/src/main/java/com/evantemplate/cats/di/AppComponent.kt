package com.evantemplate.cats.di

import com.evantemplate.cats.ui.CatListFragment
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(fragment: CatListFragment)
}