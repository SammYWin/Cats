package com.evantemplate.cats.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.evantemplate.cats.adapters.CatListAdapter
import com.evantemplate.cats.databinding.FragmentCatListBinding
import com.evantemplate.cats.viewmodels.CatListViewModel

class CatListFragment: Fragment() {

    private lateinit var binding: FragmentCatListBinding

    private val viewModel: CatListViewModel by lazy {
        ViewModelProvider(this).get(CatListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCatListBinding.inflate(inflater)

        binding.setLifecycleOwner(this)

        binding.viewModel = viewModel
        binding.rvCatsAll.adapter = CatListAdapter{ isAtLastPosition ->
            if(isAtLastPosition) viewModel.loadCats()
        }

        return binding.root
    }
}