package com.orbit.other

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.orbit.SplashScreen
import com.orbit.dashboard.animations.GravityNebula
import com.orbit.dashboard.Home
import com.orbit.dashboard.animations.SolarEclipse
import com.orbit.dashboard.animations.SolarSystem
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
        startDestination = Cons.SPLASH,
        // Forward navigation
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(300)
            )
        },

        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(300)
            )
        },

        // Back navigation
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(300)
            )
        },

        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(300)
            )
        }
    ) {
        composable(Cons.SPLASH) {SplashScreen(navController)}
        composable(Cons.ONBOARDING) {OnboardingScreen(navController)}
        composable(Cons.LOGIN) {LoginScreen(navController)}
        composable(Cons.REGISTER) { RegisterScreen(navController) }
        composable(Cons.MAINSCREEN) {Home(navController)}
        composable(Cons.SOLAR) { SolarSystem() }
        composable(Cons.SOLAR2) { SolarEclipse() }
        composable(Cons.SOLAR3) { GravityNebula() }
        composable(
            route = Cons.WEBVIEW) { backStackEntry ->

            val link = Uri.decode(backStackEntry.arguments?.getString("link"))
            val title = Uri.decode(backStackEntry.arguments?.getString("title"))

            WebView(navController, link, title)
        }
    }
}