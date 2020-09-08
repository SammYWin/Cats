package com.evantemplate.cats.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.evantemplate.cats.databinding.FragmentFavoriteCatsListBinding

class FavoriteCatsListFragment: Fragment() {

    private lateinit var binding: FragmentFavoriteCatsListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoriteCatsListBinding.inflate(inflater)

        return binding.root
    }
}