package com.github.ebrahimi16153.mvifoodapp.util.connectivity

import kotlinx.coroutines.flow.Flow

interface Connectivity {

    fun observe() : Flow<ConnectionStatus>
}