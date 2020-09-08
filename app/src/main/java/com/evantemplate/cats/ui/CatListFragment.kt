package com.evantemplate.cats.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.evantemplate.cats.databinding.FragmentCatListBinding

class CatListFragment: Fragment() {

    private lateinit var binding: FragmentCatListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCatListBinding.inflate(inflater)

        return binding.root
    }
}