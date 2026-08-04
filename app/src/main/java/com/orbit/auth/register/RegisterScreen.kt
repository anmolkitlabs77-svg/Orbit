package com.orbitwatch.ui.auth

import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.res.colorResource
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import com.orbit.R
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.orbit.network.NetworkResult
import com.orbit.auth.register.model.RegisterRequest
import com.orbit.auth.register.viewModel.registerVM
import com.orbit.other.CommonText
import com.orbit.other.GradientButton
import com.orbit.other.StarsBackground
import com.orbit.other.TextField
import com.orbit.other.cyanVioletGradient
import kotlin.random.Random


@Composable
private fun RegisterSectionEyebrow(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier
                .width(12.dp)
                .height(1.dp)
                .background(colorResource(R.color.cyan))
        )
        Spacer(Modifier.width(8.dp))
        CommonText(text.uppercase(), color = colorResource(R.color.dim), fontSize = 10.sp, letterSpacing = 2.5.sp)
    }
}

@Composable
private fun registerGradientTitle() = buildAnnotatedString {
    append("Join the ")
    withStyle(style = SpanStyle(brush = cyanVioletGradient())) {
        append("watch")
    }
}

/* -------------------------------------------------------------------- */
/*  REGISTER SCREEN                                                     */
/* -------------------------------------------------------------------- */
@Composable
fun RegisterScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var showLoader by remember { mutableStateOf(false) }

    val activity = LocalActivity.current
    val viewModel : registerVM = hiltViewModel()
    val registerState by viewModel.register.observeAsState()

    LaunchedEffect(registerState) {
        when (registerState) {
            is NetworkResult.Error<*> -> {
                showLoader = false
                Toast.makeText(activity, "Registertion failed. Please try again. ${registerState?.message}", Toast.LENGTH_SHORT).show()
            }
            is NetworkResult.Success<*> -> {
                showLoader = false
                Toast.makeText(activity, "Registertion successful!", Toast.LENGTH_SHORT).show()
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
                .padding(horizontal = 26.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(16.dp))


            Text(
                registerGradientTitle(),
                color = colorResource(R.color.ink),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 30.sp,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(10.dp))
            CommonText(
                "Create an account to get live NASA DONKI\nalerts pushed straight to your device.",
                color = colorResource(R.color.text_color2),
                fontSize = 13.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(28.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                RegisterSectionEyebrow("Your details")
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Full name",
                    leadingIcon = Icons.Filled.Person
                )
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email address",
                    leadingIcon = Icons.Filled.Email,
                    keyboardType = KeyboardType.Email
                )
            }

            Spacer(Modifier.height(20.dp))

            if(showLoader){
                CircularProgressIndicator(
                    modifier = Modifier.padding(bottom = 10.dp),
                    color = Color.White
                )
            }

            GradientButton(
                text = "Create Account",
                onClick = {
                    activity?.let {
                        viewModel.register(
                            it,
                            RegisterRequest(
                                email = email,
                                name = name
                            )
                        )
                    }

                },
                enabled = name.isNotBlank() && email.isNotBlank()
            )

            Spacer(Modifier.height(14.dp))

            CommonText(
                "By continuing you agree to Orbit Watch's Privacy Policy and Terms of Service.",
                color = colorResource(R.color.dim),
                fontSize = 10.5.sp,
                lineHeight = 16.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                CommonText("Already have an account?", color = colorResource(R.color.text_color2), fontSize = 12.5.sp)
                TextButton(onClick = {
                    navController.navigate("login")
                }) {
                    CommonText("Sign in", color = colorResource(R.color.cyan), fontSize = 12.5.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
