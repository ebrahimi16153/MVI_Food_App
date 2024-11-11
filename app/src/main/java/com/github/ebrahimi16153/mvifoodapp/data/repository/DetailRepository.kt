package com.github.ebrahimi16153.mvifoodapp.data.repository

import com.github.ebrahimi16153.mvifoodapp.data.db.FoodDao
import com.github.ebrahimi16153.mvifoodapp.data.model.FoodList
import com.github.ebrahimi16153.mvifoodapp.data.server.ApiService
import javax.inject.Inject

class DetailRepository @Inject constructor(private val apiService: ApiService, private val foodDao: FoodDao) {
    suspend fun getDetail(id: Int) = apiService.getDetailOfFood(foodId = id)
    suspend fun addFood(meal: FoodList.Meal) = foodDao.addMeal(meal = meal)
    suspend fun removeFood(meal: FoodList.Meal) = foodDao.deleteMeal(meal = meal)
    fun isExist(id: String) = foodDao.foodExists(id = id)
}