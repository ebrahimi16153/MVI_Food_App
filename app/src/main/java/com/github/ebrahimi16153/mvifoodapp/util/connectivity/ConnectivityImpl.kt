package com.github.ebrahimi16153.mvifoodapp.util.connectivity

import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


class ConnectivityImpl @Inject constructor(
    private val cm: ConnectivityManager,

//    private val networkRequest: NetworkRequest
) : Connectivity {
    override fun observe(): Flow<ConnectionStatus> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)

                    // launch is method FLOW
                    launch {
                        send(ConnectionStatus.AVAILABLE)
                    }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch {
                        send(ConnectionStatus.LOSTING)
                    }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch {
                        send(ConnectionStatus.LOST)
                    }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch {
                        send(ConnectionStatus.UNAVAILABLE)
                    }
                }
            }
             // for android N or less than N
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
//                cm.registerDefaultNetworkCallback(callback)
//            }else{
//                cm.registerNetworkCallback(networkRequest, callback)
//            }

            cm.registerDefaultNetworkCallback(callback)

            awaitClose{
                cm.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }
}