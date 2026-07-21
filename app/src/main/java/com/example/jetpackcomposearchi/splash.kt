package com.example.jetpackcomposearchi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jetpackcomposearchi.dashboard.base.SharedPref
import com.example.jetpackcomposearchi.other.Cons
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController){
    LaunchedEffect(Unit) {
        delay(3000)

        navController.navigate("mainScreen"){
                popUpTo("splash"){
                    inclusive = true
                }
            }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier =  Modifier.fillMaxSize()
            .background(color = colorResource(R.color.black))
        ){

        Column(
            modifier = Modifier.background(color = colorResource(R.color.black)),
            horizontalAlignment = Alignment.CenterHorizontally,) {

            Text("Orbit", fontSize = 30.sp, color = Color.White)
        }

    }
}