package com.github.ebrahimi16153.mvifoodapp.view.home

import com.github.ebrahimi16153.mvifoodapp.data.model.Categories
import com.github.ebrahimi16153.mvifoodapp.data.model.FoodList

sealed class HomeState {

    data object Empty : HomeState()
    data class  Error(val message: String) : HomeState()
    data object Loading : HomeState()
    data class RandomMeal(val meal: FoodList.Meal) : HomeState()
    data class CategoryList(val list: List<Categories.Category>) : HomeState()
    data class Foods(val list: List<FoodList.Meal>) : HomeState()

}