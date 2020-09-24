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
import com.evantemplate.cats.interactors.Interactor
import com.evantemplate.cats.models.Cat
import com.evantemplate.cats.network.CatApi
import com.evantemplate.cats.presenters.FavoriteCatListPresenter
import com.evantemplate.cats.repositories.DbCatsRepository
import com.evantemplate.cats.repositories.NetCatsRepository
import kotlinx.android.synthetic.main.fragment_favorite_cats_list.view.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class FavoriteCatsListFragment: MvpAppCompatFragment(), FavoriteCatListView {

    @InjectPresenter
    lateinit var presenter: FavoriteCatListPresenter
    lateinit var adapter: CatListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_favorite_cats_list, container, false)

        adapter = CatListAdapter({},
            {btn, cat ->
                if(btn.id == R.id.btn_fav){
                    presenter.deleteCatFromFav(cat)}
                else {
                        val manager: DownloadManager by lazy { requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager }
                        presenter.downloadImage(manager, cat.imgUrl)
                }
            }
        )


        rootView.rv_cats_favorite.adapter = adapter

        return rootView
    }

    override fun updateFavCatList(catList: List<Cat>) {
        adapter.updateData(catList)
    }


    @ProvidePresenter
    fun providePresenter(): FavoriteCatListPresenter{
        val context = requireContext()
        val dataBase = CatDatabase.getInstance(context).catDao

        val netCatsRepository = NetCatsRepository(CatApi.retrofitService)
        val dbCatsRepository = DbCatsRepository(dataBase)

        val interactor = Interactor(netCatsRepository, dbCatsRepository)

        return FavoriteCatListPresenter(interactor)
    }
}