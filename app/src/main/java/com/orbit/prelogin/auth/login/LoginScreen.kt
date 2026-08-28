package com.orbitwatch.ui.auth

import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.orbit.other.StarsBackground
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.navigation.NavHostController
import com.orbit.R
import com.orbit.dashboard.base.App
import com.orbit.prelogin.auth.login.viewModel.loginVM
import com.orbit.network.NetworkResult
import com.orbit.other.CommonText
import com.orbit.other.Cons
import com.orbit.other.GradientButton
import com.orbit.other.TextField
import com.orbit.other.cyanVioletGradient
import com.orbit.other.fieldText

@Composable
private fun loginGradientTitle() = buildAnnotatedString {
    append("Welcome ")
    withStyle(style = SpanStyle(brush = cyanVioletGradient())) {
        append("back")
    }
}
@Composable
fun LoginScreen(navController: NavHostController) {

    val activity = LocalActivity.current

    val scrollState = rememberScrollState()
    val viewModel : loginVM = hiltViewModel()
    val showLoader by viewModel.displayLoader.observeAsState(false)
    val email by viewModel.email.observeAsState("")
    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

    LaunchedEffect(Unit) {

        viewModel.loginEvent.collect { message ->

            Toast.makeText(
                activity,
                message,
                Toast.LENGTH_SHORT
            ).show()


            if (message == "Login successful!") {

                App.sharedPref.putBoolean(Cons.IS_USER_LOGGEDIN,true)
                App.sharedPref.putBoolean(Cons.IS_GUEST,false)



                navController.navigate(Cons.MAINSCREEN) {
                    popUpTo(0) {
                        inclusive = true
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .safeDrawingPadding()
            .fillMaxSize()
    ) {
        StarsBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 26.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))

            Icon(
                modifier = Modifier.height(84.dp),
                painter = painterResource(R.drawable.logo,),
                contentDescription = "logo",
                tint = Color.Unspecified
            )
            Spacer(Modifier.height(24.dp))

            Text(
                loginGradientTitle(),
                color = colorResource(R.color.ink),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(8.dp))
            CommonText(
                "Sign in to keep tracking live solar flares,\nCMEs and geomagnetic storms.",
                color = colorResource(R.color.text_color2),
                fontSize = 13.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(32.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                fieldText("Account")
                TextField(
                    value = email,
                    onValueChange = {
                        viewModel.updateEmail(it)
                                    },
                    label = "Email address",
                    leadingIcon = Icons.Filled.Email,
                    keyboardType = KeyboardType.Email
                )
            }

            Spacer(Modifier.height(16.dp))

            if(showLoader){
                CircularProgressIndicator(
                    modifier = Modifier.padding(bottom = 10.dp),
                    color = Color.White
                )
            }

            GradientButton(
                text = "Sign In",
                onClick = {

                    if(email.matches(emailRegex)) {
                        activity?.let {
                            viewModel.login(
                                it,)
                        }
                    }
                    else {
                        Toast.makeText(activity, "Invalid email address", Toast.LENGTH_SHORT).show()

                    }
                },
                enabled = email.isNotBlank()
            )

            CommonText(name = "Or", color = Color.White, modifier = Modifier.padding(10.dp))

            Box(
                modifier = Modifier.fillMaxWidth()
                    .clickable{
                        App.sharedPref.putBoolean(Cons.IS_USER_LOGGEDIN,true)
                        App.sharedPref.putBoolean(Cons.IS_GUEST,true)
                        navController.navigate(Cons.MAINSCREEN){
                            popUpTo(0){
                                inclusive = true
                            }
                        }
                    }
                    .height(52.dp)
                    .border(
                        width = 1.dp,
                        color = colorResource(R.color.app_blue),
                        shape = RoundedCornerShape(16.dp),
                    ),
                contentAlignment = Alignment.Center
            ){
                CommonText(
                    modifier = Modifier.padding(10.dp),
                    name = "CONTINUE AS GUEST",
                    fontSize = 14.sp,
                    color = Color.White,)
            }

            Spacer(Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                CommonText(name = "Don't have an account?", color = colorResource(R.color.dim), fontSize = 12.5.sp)
                TextButton(onClick = {
                    navController.navigate(Cons.REGISTER){
                        popUpTo(0){
                            inclusive = true
                        }
                    }
                }) {
                    CommonText(name = "Create one", color = colorResource(R.color.cyan), fontSize = 12.5.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

