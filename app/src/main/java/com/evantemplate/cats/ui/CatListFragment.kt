package com.evantemplate.cats.ui

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.evantemplate.cats.R
import com.evantemplate.cats.adapters.CatListAdapter
import com.evantemplate.cats.database.CatDao
import com.evantemplate.cats.database.CatDatabase
import com.evantemplate.cats.di.AppModule
import com.evantemplate.cats.di.DaggerAppComponent
import com.evantemplate.cats.interactors.Interactor
import com.evantemplate.cats.models.Cat
import com.evantemplate.cats.network.CatApi
import com.evantemplate.cats.presenters.CatListPresenter
import com.evantemplate.cats.repositories.DbCatsRepository
import com.evantemplate.cats.repositories.NetCatsRepository
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_cat_list.view.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class CatListFragment: MvpAppCompatFragment(), CatListView {

    @Inject
    lateinit var presenterlazy: dagger.Lazy<CatListPresenter>

    @InjectPresenter
    lateinit var presenter: CatListPresenter
    lateinit var adapter: CatListAdapter

    @ProvidePresenter
    fun providePresenter(): CatListPresenter{

        DaggerAppComponent.builder()
            .appModule(AppModule(requireContext()))
            .build()
            .inject(this)

        return presenterlazy.get()
    }

//    @Inject
//    lateinit var dataBase: CatDao

//    @Inject
//    lateinit var dbCatsRepository: DbCatsRepository

//    @Inject
//    lateinit var netCatsRepository: NetCatsRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_cat_list, container, false)

        adapter = CatListAdapter(
            { isOnLastPosition -> if (isOnLastPosition) presenter.loadCats() },
            { btn, cat ->
                if(btn.id == R.id.btn_fav){
                    if(!cat.isInFavorites) presenter.addToFavoritesBtnPressed(cat)
                    else presenter.deleteFromFavorite(cat)
                } else{
                    val manager: DownloadManager by lazy { requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager }
                    presenter.downloadImage(manager, cat.imgUrl)
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