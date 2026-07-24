package com.orbit.dashboard.webview

import android.webkit.WebView
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.orbit.other.Cons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebView(navController: NavController,link: Int) {

    var webView: WebView? = null
    var Link : String?= null
    var Title : String? = null

    if (link == 1){
        Link = Cons.PRIVACY_POLICY_URL
        Title ="Privacy Policy"
    }
    else if(link == 2){
        Link = Cons.TERMS_CONDITION_URL
        Title = "Terms and Conditions"
    }

    BackHandler {
        handleBack(webView) {
            navController.popBackStack()
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(Title!!) },
                navigationIcon = {
                    IconButton(onClick = {

                            handleBack(webView) {
                                navController.popBackStack()
                        }
                    }) {
                    }
                }
            )
        }
    ) { padding ->

        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    loadUrl(Link!!)
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        )
    }
}

fun handleBack(webView: WebView?, onExit: () -> Unit) {
    if (webView != null && webView.canGoBack()) {
        webView.goBack()
    } else {
        onExit()
    }
}