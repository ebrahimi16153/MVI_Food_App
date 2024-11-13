package com.github.ebrahimi16153.mvifoodapp.view.fav

sealed class FavIntent {

    data object FavoriteList: FavIntent()

}