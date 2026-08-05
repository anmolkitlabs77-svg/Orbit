package com.orbit.dashboard.webview

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.orbit.other.topAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebView(navController: NavController, link: String, title: String?) {

    var webView: WebView? by remember { mutableStateOf(null) }

    BackHandler {
        if (webView?.canGoBack() == true) {
            webView?.goBack()
        } else {
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            topAppBar(title ?: "","",true,{
                handleBack(webView) {
                    navController.popBackStack()
                }
            })

        }
    ) { padding ->
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            factory = { context ->
                WebView(context).apply {

                    webView = this

                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true

                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            request: WebResourceRequest?
                        ): Boolean {
                            request?.url?.let {
                                view?.loadUrl(it.toString())
                            }
                            return true
                        }
                    }

                    loadUrl(link)
                }
            }
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