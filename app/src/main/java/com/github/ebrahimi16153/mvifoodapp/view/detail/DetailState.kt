package com.github.ebrahimi16153.mvifoodapp.view.detail

import com.github.ebrahimi16153.mvifoodapp.data.model.FoodList

sealed class DetailState {
    data class Error(val message: String): DetailState()
    data object Loading : DetailState()
    data class ShowDetail(val detail: FoodList.Meal): DetailState()
    data class IsExist(val exist: Boolean): DetailState()
    data class Add(var unit: Unit): DetailState()
    data class Remove(var unit: Unit): DetailState()
}