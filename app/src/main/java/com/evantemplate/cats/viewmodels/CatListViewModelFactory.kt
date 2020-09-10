package com.evantemplate.cats.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evantemplate.cats.database.CatDao

class CatListViewModelFactory(private val database: CatDao):
    ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatListViewModel::class.java)) {
            return CatListViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}