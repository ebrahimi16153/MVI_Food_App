package com.github.ebrahimi16153.mvifoodapp.data.repository

import com.github.ebrahimi16153.mvifoodapp.data.db.FoodDao
import javax.inject.Inject


class FavRepository @Inject constructor(private val favDao: FoodDao)  {

    suspend fun getListOfFav() = favDao.getAllMeal()

}