package com.github.ebrahimi16153.foodapp.util.base

interface BaseView {

    fun showLoading()
    fun hideLoading()
    fun chckInternet():Boolean
    fun internetError(hasInternet: Boolean)
    fun serverError(message:String)

}