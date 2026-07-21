package com.orbit.other

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.mutableStateOf

class NetworkChange : BroadcastReceiver() {

    var isNetworkConnected = mutableStateOf(true)
    private var listener: NetworkConnectivityListener? = null

    override fun onReceive(context: Context, intent: Intent) {
        if (!Common.isConnectedToInternet(context)) {
            Log.d("NetworkChange","Internet not connected")
            isNetworkConnected.value = false
        }
        else {
            Log.d("NetworkChange","Internet Connected")
            isNetworkConnected.value = true
            listener?.onNetworkConnected()
        }
    }

    interface NetworkConnectivityListener {
        fun onNetworkConnected()
    }
}