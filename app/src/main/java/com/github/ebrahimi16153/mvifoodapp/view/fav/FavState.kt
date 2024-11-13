package com.github.ebrahimi16153.mvifoodapp.view.fav

import com.github.ebrahimi16153.mvifoodapp.data.model.FoodList

sealed class FavState {

    data object Empty: FavState()
    data class FavList(val list: List<FoodList.Meal>): FavState()

}