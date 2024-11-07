package com.github.ebrahimi16153.mvifoodapp.data.server

import com.github.ebrahimi16153.mvifoodapp.data.model.Categories
import com.github.ebrahimi16153.mvifoodapp.data.model.FoodList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("random.php")
    suspend fun getRandomFood(): Response<FoodList>

    @GET("categories.php")
    suspend fun getCategories(): Response<Categories>

    @GET("search.php")
    suspend fun getListOfFood(@Query("f") firstLetter: String): Response<FoodList>

    @GET("search.php")
    suspend fun searchQuery(@Query("s") searchQuery: String): Response<FoodList>


    @GET("filter.php")
    suspend fun getByCategory(@Query("c") categoryName: String): Response<FoodList>

    @GET("lookup.php")
    suspend fun getDetailOfFood(@Query("i") foodId: Int): Response<FoodList>


}