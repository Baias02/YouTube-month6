package com.example.youtube_month6.internet

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData

class ConnectionInternet(context: Context): LiveData<Boolean>() {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            updateConnectionStatus(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            updateConnectionStatus(false)
        }

//        override fun onUnavailable() {
//            super.onUnavailable()
//            updateConnectionStatus(false)
//        }
//
//        override fun onLosing(network: Network, maxMsToLive: Int) {
//            super.onLosing(network, maxMsToLive)
//            updateConnectionStatus(false)
//        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            val isConnected = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            updateConnectionStatus(isConnected)
        }
    }

    override fun onActive() {
        super.onActive()
        registerNetworkCallback()

    }

    override fun onInactive() {
        super.onInactive()
        unregisterNetworkCallback()
    }

    private fun registerNetworkCallback(){
        val builder = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        connectivityManager.registerNetworkCallback(builder.build(),networkCallback)
    }

    private fun unregisterNetworkCallback(){
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private fun updateConnectionStatus(isConnected: Boolean) {
        if (value != isConnected) postValue(isConnected)
    }

}