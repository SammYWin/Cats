package com.evantemplate.cats.di

import com.evantemplate.cats.ui.CatListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeCatListFragment(): CatListFragment
}