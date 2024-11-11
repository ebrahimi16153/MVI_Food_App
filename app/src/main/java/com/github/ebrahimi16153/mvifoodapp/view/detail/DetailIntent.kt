package com.github.ebrahimi16153.mvifoodapp.view.detail

import com.github.ebrahimi16153.mvifoodapp.data.model.FoodList

sealed  class DetailIntent {
    data class DetailMeal(val id: String): DetailIntent()
    data class ISExist(val id: String): DetailIntent()
    data class Add(val meal: FoodList.Meal): DetailIntent()
    data class Remove(val meal: FoodList.Meal): DetailIntent()
}