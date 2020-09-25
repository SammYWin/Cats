package com.evantemplate.cats.ui

import android.app.DownloadManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.evantemplate.cats.R
import com.evantemplate.cats.adapters.CatListAdapter
import com.evantemplate.cats.di.AppModule
import com.evantemplate.cats.di.DaggerAppComponent
import com.evantemplate.cats.models.Cat
import com.evantemplate.cats.presenters.CatListPresenter
import dagger.Lazy
import kotlinx.android.synthetic.main.fragment_cat_list.view.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class CatListFragment: MvpAppCompatFragment(), CatListView {

    @Inject
    lateinit var presenterLazy: Lazy<CatListPresenter>

    @InjectPresenter
    lateinit var presenter: CatListPresenter

    lateinit var adapter: CatListAdapter

    @ProvidePresenter
    fun providePresenter(): CatListPresenter{
        DaggerAppComponent.builder()
            .appModule(AppModule(requireContext()))
            .build()
            .inject(this)

        return presenterLazy.get()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_cat_list, container, false)

        adapter = CatListAdapter(
            { isOnLastPosition -> if (isOnLastPosition) presenter.loadCats() },
            { btn, cat ->
                if(btn.id == R.id.btn_fav){
                    if(!cat.isInFavorites) presenter.addToFavoritesBtnPressed(cat)
                    else presenter.deleteFromFavorite(cat)
                } else{
                    presenter.downloadImage(cat.imgUrl)
                }
            }
        )

        rootView.rv_cats_all.adapter = adapter

        return rootView
    }

    override fun showAllCats(catList: List<Cat>) {
        adapter.updateData(catList)
    }
}