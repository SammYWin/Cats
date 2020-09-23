package com.evantemplate.cats.ui

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
import com.evantemplate.cats.presenters.CatListPresenter
import com.evantemplate.cats.repositories.DbCatsRepository
import com.evantemplate.cats.repositories.NetCatsRepository
import kotlinx.android.synthetic.main.fragment_cat_list.view.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter


class CatListFragment: MvpAppCompatFragment(), CatListView {

    @InjectPresenter
    lateinit var presenter: CatListPresenter
    lateinit var adapter: CatListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_cat_list, container, false)

        adapter = CatListAdapter(
            { isOnLastPosition -> if (isOnLastPosition) presenter.loadCats() },
            { cat -> presenter.addToFavoritesBtnPressed(cat)}
        )

        rootView.rv_cats_all.adapter = adapter

        return rootView
    }

    override fun showAllCats(catList: List<Cat>) {
        adapter.updateData(catList)
    }

    @ProvidePresenter
    fun providePresenter(): CatListPresenter{
        val application = requireContext()
        val dataBase = CatDatabase.getInstance(application).catDao

        val netCatsRepository = NetCatsRepository(CatApi.retrofitService)
        val dbCatsRepository = DbCatsRepository(dataBase)

        val interactor = Interactor(netCatsRepository, dbCatsRepository)

        return CatListPresenter(interactor)
    }
}