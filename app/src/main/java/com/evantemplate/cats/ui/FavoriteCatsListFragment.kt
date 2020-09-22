package com.evantemplate.cats.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.evantemplate.cats.adapters.CatListAdapter
import com.evantemplate.cats.database.CatDatabase

class FavoriteCatsListFragment: Fragment() {

//    private lateinit var binding: FragmentFavoriteCatsListBinding
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        binding = FragmentFavoriteCatsListBinding.inflate(inflater)
//
//        val application = requireNotNull(this.activity).application
//
//        val dataBase = CatDatabase.getInstance(application).catDao
//
//        val viewModel = FavCatListViewModel(dataBase)
//
//        binding.setLifecycleOwner(this)
//
//        binding.viewModel = viewModel
//        binding.rvCatsFavorite.adapter = CatListAdapter(
//            {},
//            {cat -> viewModel.deleteFromFavorites(cat)}
//        )
//
//        return binding.root
//    }
}