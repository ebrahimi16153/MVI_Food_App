package com.github.ebrahimi16153.mvifoodapp.util.connectivity

import android.net.ConnectivityManager
import android.net.NetworkRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject



class ConnectivityImpl @Inject constructor(
    private val cm :ConnectivityManager,
    private val networkRequest: NetworkRequest) : Connectivity {
    override fun observe(): Flow<ConnectionStatus> {
        TODO("Not yet implemented")
    }


}