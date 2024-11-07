package com.github.ebrahimi16153.mvifoodapp.view.home


sealed class HomeIntent {

    data object RandomMeal: HomeIntent()
    data object Category: HomeIntent()
    data class FoodLetters(val letter: String): HomeIntent()
    data class FoodsByCategory(val category: String) : HomeIntent()
    data class FoodsBySearch(val searchQuery: String): HomeIntent()

}