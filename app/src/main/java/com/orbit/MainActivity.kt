package com.orbit

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.orbit.other.AppNavigation
import com.orbit.other.NetworkChange
import com.orbit.other.SystemBars
import com.orbit.ui.theme.JetpackComposeArchiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val networkChange = NetworkChange()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            JetpackComposeArchiTheme {
                SystemBars()
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(color = colorResource(R.color.black))

                    ,
                ) {

                    AppNavigation()

                    if (!networkChange.isNetworkConnected.value) {
//                        NoInternet()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(networkChange,IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onStop() {
        unregisterReceiver(networkChange)
        super.onStop()
    }
}