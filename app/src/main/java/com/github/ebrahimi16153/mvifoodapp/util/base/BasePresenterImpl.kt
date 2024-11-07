package com.github.ebrahimi16153.foodapp.util.base

import io.reactivex.rxjava3.disposables.Disposable


open class BasePresenterImpl : BasePresenter {

    var disposable: Disposable? = null
    override fun onStop() {
        disposable?.dispose()
    }
}