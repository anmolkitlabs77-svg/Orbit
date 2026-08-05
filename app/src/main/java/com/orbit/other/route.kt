package com.orbit.other

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.orbit.SplashScreen
import com.orbit.dashboard.Home
import com.orbit.dashboard.webview.WebView
import com.orbit.prelogin.onboarding.OnboardingScreen
import com.orbitwatch.ui.auth.LoginScreen
import com.orbitwatch.ui.auth.RegisterScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash") {
        composable("splash") {SplashScreen(navController)}
        composable("onboarding") {OnboardingScreen(navController)}
        composable("login") {LoginScreen(navController)}
        composable("register") { RegisterScreen(navController) }
        composable("mainScreen") {Home()}
        composable(
            route = "webView/{link}") { backStackEntry ->
            val link = backStackEntry.arguments?.getString("link")?.toInt() ?: 1
            WebView(navController, link)
        }
    }
}