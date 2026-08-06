package com.orbit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.orbit.dashboard.base.App
import com.orbit.other.CommonText
import com.orbit.other.Cons
import com.orbit.other.StarsBackground
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun SplashScreen(navController: NavController){

    LaunchedEffect(Unit) {
        delay(2000.milliseconds)

        if(App.sharedPref.getBoolean(Cons.IS_USER_LOGGEDIN,false)){

            navController.navigate(Cons.MAINSCREEN){
                popUpTo("splash"){
                    inclusive = true
                }
            }
        }
        else if(App.sharedPref.getBoolean(Cons.IS_ONBOARDING_COMPLETE,false)){

            navController.navigate(Cons.LOGIN){
                popUpTo("splash"){
                    inclusive = true
                }
            }
        }
        else {
            navController.navigate(Cons.ONBOARDING){
                popUpTo("splash"){
                    inclusive = true
                }
            }
        }

    }
    Box(
        contentAlignment = Alignment.Center,
        modifier =  Modifier.fillMaxSize()
            .safeDrawingPadding()
            .background(color = colorResource(R.color.black))
        ){
        StarsBackground()

        Column(
            modifier = Modifier.background(color = colorResource(R.color.black)),
            horizontalAlignment = Alignment.CenterHorizontally,) {

            CommonText(name = "Orbit",
                fontSize = 30.sp,
                color = Color.White)
        }

    }
}