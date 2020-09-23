package com.evantemplate.cats.ui

import com.evantemplate.cats.models.Cat
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface FavoriteCatListView: MvpView {
    fun updateFavCatList(catList: List<Cat>)
}