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
import androidx.navigation.NavHostController
import com.orbit.R
import com.orbit.dashboard.base.App
import com.orbit.prelogin.auth.login.viewModel.loginVM
import com.orbit.network.NetworkResult
import com.orbit.other.CommonText
import com.orbit.other.Cons
import com.orbit.other.GradientButton
import com.orbit.other.TextField
import com.orbit.other.fieldText

@Composable
fun LoginScreen(navController: NavHostController) {

    var email by remember { mutableStateOf("") }
    var showLoader by remember { mutableStateOf(false) }
    val activity = LocalActivity.current
    val scrollState = rememberScrollState()

    val viewModel : loginVM = hiltViewModel()
    val loginState by viewModel.login.observeAsState()

    LaunchedEffect(loginState) {
        when (loginState) {
            is NetworkResult.Error<*> -> {
                showLoader = false
                App.sharedPref.putBoolean(Cons.IS_USER_LOGGEDIN,true)
                Toast.makeText(activity, "Login failed. Please try again. ", Toast.LENGTH_SHORT).show()
                navController.navigate(Cons.MAINSCREEN){
                    popUpTo(Cons.LOGIN){
                        inclusive = true
                    }
                }

            }
            is NetworkResult.Success<*> -> {
                showLoader = false
                Toast.makeText(activity, "Login successful!", Toast.LENGTH_SHORT).show()
            }
            is NetworkResult.Loading<*> -> {
                showLoader = true
            }

            else -> {}
        }
    }


    Box(
        modifier = Modifier
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

            Box(
                modifier = Modifier
                    .size(84.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(Color(0xFF2C2960), Color(0xFF12142C), Color(0xFF0A0B1C))
                        )
                    )
                    .border(1.dp, colorResource(R.color.line), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Lock, null, tint = colorResource(R.color.cyan), modifier = Modifier.size(32.dp))
            }

            Spacer(Modifier.height(24.dp))

            CommonText(
                "Welcome back",
                color = colorResource(R.color.ink),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(8.dp))
            CommonText(
                "Sign in to keep tracking live solar flares,\nCMEs and geomagnetic storms.",
                color = colorResource(R.color.dim),
                fontSize = 13.sp,
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
                    onValueChange = { email = it },
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
                    activity?.let {
                        viewModel.login(
                            it,
                            email
                        )
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
                            popUpTo(Cons.LOGIN){
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
                    navController.navigate(Cons.REGISTER)
                }) {
                    CommonText(name = "Create one", color = colorResource(R.color.cyan), fontSize = 12.5.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

