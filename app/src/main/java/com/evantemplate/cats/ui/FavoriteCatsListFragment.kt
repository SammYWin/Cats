package com.evantemplate.cats.ui

import android.app.DownloadManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.evantemplate.cats.R
import com.evantemplate.cats.adapters.CatListAdapter
import com.evantemplate.cats.database.CatDatabase
import com.evantemplate.cats.di.AppModule
import com.evantemplate.cats.di.DaggerAppComponent
import com.evantemplate.cats.interactors.Interactor
import com.evantemplate.cats.models.Cat
import com.evantemplate.cats.network.CatApi
import com.evantemplate.cats.presenters.FavoriteCatListPresenter
import com.evantemplate.cats.repositories.DbCatsRepository
import com.evantemplate.cats.repositories.NetCatsRepository
import dagger.Lazy
import kotlinx.android.synthetic.main.fragment_favorite_cats_list.view.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class FavoriteCatsListFragment: MvpAppCompatFragment(), FavoriteCatListView {
    @Inject
    lateinit var presenterLazy: Lazy<FavoriteCatListPresenter>

    @InjectPresenter
    lateinit var presenter: FavoriteCatListPresenter

    lateinit var adapter: CatListAdapter

    @ProvidePresenter
    fun providePresenter(): FavoriteCatListPresenter{
        DaggerAppComponent.builder()
            .appModule(AppModule(requireContext()))
            .build()
            .inject(this)

        return presenterLazy.get()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_favorite_cats_list, container, false)

        adapter = CatListAdapter({},
            {btn, cat ->
                if(btn.id == R.id.btn_fav){
                    presenter.deleteCatFromFav(cat)}
                else {
                    presenter.downloadImage(cat.imgUrl)
                }
            }
        )
        rootView.rv_cats_favorite.adapter = adapter

        return rootView
    }


    override fun updateFavCatList(catList: List<Cat>) {
        adapter.updateData(catList)
    }
}