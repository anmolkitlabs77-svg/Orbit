package com.example.jetpackcomposearchi.dashboard

import android.Manifest
import com.example.jetpackcomposearchi.R
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposearchi.dashboard.apod.Apod
import com.example.jetpackcomposearchi.dashboard.events.Events
import com.example.jetpackcomposearchi.dashboard.neos.Neos
import com.example.jetpackcomposearchi.dashboard.weather.Weather
import com.example.jetpackcomposearchi.other.helper
import com.example.jetpackcomposearchi.other.rememberPermissionManager
import com.example.jetpackcomposearchi.other.topAppBar

data class bottomBarItems(
    val title: String,
    val selectedIcon : Int,
    val hasNews : Boolean,
    val badgeCount : Int? = null,
    val route : String
)
val items = listOf(

    bottomBarItems(
        title = "Apod",
        selectedIcon = R.drawable.home,
        hasNews = false,
        route = "apod"
    ),
    bottomBarItems(
        title = "Neos",
        selectedIcon = R.drawable.asteriod,
        hasNews = false,
        route = "neos"
    ),
    bottomBarItems(
        title = "Events",
        selectedIcon = R.drawable.events,
        hasNews = false,
        route = "events",
    ),
    bottomBarItems(
        title = "Weather",
        selectedIcon = R.drawable.weather,
        hasNews = false,
        route = "weather"
    ),
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home() {

    val navController1 = rememberNavController()
    val navBackStackEntry by navController1.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    val manager = rememberPermissionManager(
        permissions = listOf(Manifest.permission.POST_NOTIFICATIONS)
    ) { result ->
        if (result.granted) {
            Log.d("Permission","Permission is granted")
        }
        else if(result.permanentlyDenied){
            Log.d("Permission","Permission is permanently denied")
        }
    }

    LaunchedEffect(Unit) {

        manager.request()
    }

    Scaffold(
        topBar = {
            AppTopBar(currentRoute)
        },
        bottomBar = {
            NavigationBar(
                containerColor = colorResource(R.color.black),
                ) {
                items.forEachIndexed { index, item ->

                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = colorResource(R.color.white),
                            selectedTextColor = colorResource(R.color.white)

                        ),
                        selected = currentRoute == item.route,
                        onClick = {
                            navController1.navigate(item.route) {
                                popUpTo(navController1.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = {
                            Text(
                                item.title,
                                color = Color.White,
                             )
                        },
                        alwaysShowLabel = true,
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (item.badgeCount != null) {
                                        Badge {
                                            Text(text = item.badgeCount.toString())
                                        }
                                    } else if (item.hasNews) {
                                        Badge()
                                    }
                                }
                            ){
                                Icon(
                                    modifier = Modifier.height(25.dp),
                                    tint = colorResource(R.color.app_black),
                                    painter = painterResource(item.selectedIcon),
                                    contentDescription = ""
                                    )
                            }
                        }

                    )
                }
            }
        },


    ) { innerPadding ->
        NavHost(
            navController = navController1,
            startDestination = "apod",
            modifier = Modifier.padding(innerPadding)
        ) {

            composable("apod") {Apod()}

            composable("neos") {Neos()}

            composable("events") {Events()}

            composable("weather") {Weather()}
        }
    }
}

@Composable
fun AppTopBar(route: String?){

    val date = helper.currentDate()
    when(route) {

        "apod" -> {
            topAppBar(
                title = "Picture of the Day",
                subtitle = date + " - NASA APOD"
            )
        }

        "neos" -> {
            topAppBar(
                title = "Near-Earth Objects",
                subtitle = "$date - asteroid tracker"
            )
        }

        "events" -> {
            topAppBar(
                title = "Earth Events",
                subtitle = "NASA EONET - Live Natural Events"
            )
        }

        "weather" -> {
            topAppBar(
                title = "Space Weather",
                subtitle = "$date - CCMC DONKI feed"
            )
        }
    }
}