package com.example.jetpackcomposearchi

import android.app.Activity
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.example.jetpackcomposearchi.other.AppNavigation
import com.example.jetpackcomposearchi.other.NetworkChange
import com.example.jetpackcomposearchi.other.SystemBars
import com.example.jetpackcomposearchi.other.dialog.NoInternet
import com.example.jetpackcomposearchi.ui.theme.JetpackComposeArchiTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

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