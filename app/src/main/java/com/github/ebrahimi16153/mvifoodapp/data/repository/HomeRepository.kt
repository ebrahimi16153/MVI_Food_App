package com.github.ebrahimi16153.mvifoodapp.data.repository

import com.github.ebrahimi16153.mvifoodapp.data.server.ApiService
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getRandomMeal() = apiService.getRandomFood()
    suspend fun getCategory() = apiService.getCategories()
    suspend fun getFoodsByFirstLetter(letter: String) = apiService.getListOfFood(letter)
    suspend fun getFoodsByCategory(category: String) = apiService.getByCategory(category)
    suspend fun getFoodsBySearch(searchQuery: String) = apiService.searchQuery(searchQuery)

}