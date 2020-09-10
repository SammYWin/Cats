package com.evantemplate.cats.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.evantemplate.cats.adapters.CatListAdapter
import com.evantemplate.cats.database.CatDatabase
import com.evantemplate.cats.databinding.FragmentFavoriteCatsListBinding
import com.evantemplate.cats.viewmodels.CatListViewModel
import com.evantemplate.cats.viewmodels.CatListViewModelFactory
import com.evantemplate.cats.viewmodels.FavCatListViewModel
import com.evantemplate.cats.viewmodels.FavCatViewModelFactory

class FavoriteCatsListFragment: Fragment() {

    private lateinit var binding: FragmentFavoriteCatsListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoriteCatsListBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application

        val dataBase = CatDatabase.getInstance(application).catDao

        val viewModelFactory = FavCatViewModelFactory(dataBase)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(FavCatListViewModel::class.java)

        binding.setLifecycleOwner(this)

        binding.viewModel = viewModel
        binding.rvCatsFavorite.adapter = CatListAdapter(
            {},
            {cat -> viewModel.deleteFromFavorites(cat)}
        )

        return binding.root
    }
}