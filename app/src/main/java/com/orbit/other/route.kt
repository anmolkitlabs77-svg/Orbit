package com.orbit.other

import android.net.Uri
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
        startDestination = Cons.SPLASH) {
        composable(Cons.SPLASH) {SplashScreen(navController)}
        composable(Cons.ONBOARDING) {OnboardingScreen(navController)}
        composable(Cons.LOGIN) {LoginScreen(navController)}
        composable(Cons.REGISTER) { RegisterScreen(navController) }
        composable(Cons.MAINSCREEN) {Home(navController)}
        composable(
            route = Cons.WEBVIEW) { backStackEntry ->

            val link = Uri.decode(backStackEntry.arguments?.getString("link"))
            val title = Uri.decode(backStackEntry.arguments?.getString("title"))

            WebView(navController, link, title)
        }
    }
}