package com.orbitwatch.ui.auth

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlin.random.Random

/* -------------------------------------------------------------------- */
/*  Palette — lifted from the Orbit Watch HTML design tokens             */
/* -------------------------------------------------------------------- */
private object LoginColors {
    val Void = Color(0xFF060814)
    val Line = Color(0xFF1C2440)
    val Violet = Color(0xFF8B7BFF)
    val Cyan = Color(0xFF3FE0D0)
    val Ink = Color(0xFFEEF1FB)
    val Dim = Color(0xFF5B6690)
    val MonoDim = Color(0xFF46517A)
    val FieldBg = Color(0xFF080A15)

    val cyanVioletGradient = Brush.linearGradient(listOf(Cyan, Violet))
}

@Preview
@Composable
private fun LoginStarsBackground(modifier: Modifier = Modifier, starCount: Int = 40) {
    val stars = remember {
        List(starCount) {
            Triple(Random.nextFloat(), Random.nextFloat(), Random.nextFloat() * 0.5f + 0.3f)
        }
    }
    Canvas(modifier = modifier.fillMaxSize()) {
        stars.forEach { (xPct, yPct, alpha) ->
            drawCircle(
                color = Color(0xFFCFD8FF).copy(alpha = alpha),
                radius = 1.6f,
                center = Offset(size.width * xPct, size.height * yPct)
            )
        }
    }
}

@Composable
private fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: androidx.compose.ui.graphics.vector.ImageVector,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var visible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        placeholder = { Text(label, color = LoginColors.Dim, fontSize = 13.sp) },
        leadingIcon = { Icon(leadingIcon, null, tint = LoginColors.Cyan) },
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { visible = !visible }) {
                    Icon(
                        if (visible) Icons.Filled.Person else Icons.Filled.Favorite,
                        contentDescription = null,
                        tint = LoginColors.Dim
                    )
                }
            }
        },
        visualTransformation = if (isPassword && !visible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = LoginColors.FieldBg,
            unfocusedContainerColor = LoginColors.FieldBg,
            disabledContainerColor = LoginColors.FieldBg,
            focusedBorderColor = LoginColors.Cyan,
            unfocusedBorderColor = LoginColors.Line,
            focusedTextColor = LoginColors.Ink,
            unfocusedTextColor = LoginColors.Ink,
            cursorColor = LoginColors.Cyan
        )
    )
}

@Composable
private fun LoginGradientButton(text: String, onClick: () -> Unit, enabled: Boolean = true) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(if (enabled) LoginColors.cyanVioletGradient else SolidColor(LoginColors.Line)),
        contentAlignment = Alignment.Center
    ) {
        TextButton(onClick = onClick, enabled = enabled, modifier = Modifier.fillMaxSize()) {
            Text(
                text,
                color = if (enabled) LoginColors.Void else LoginColors.Dim,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun LoginSectionEyebrow(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier
                .width(12.dp)
                .height(1.dp)
                .background(LoginColors.Cyan)
        )
        Spacer(Modifier.width(8.dp))
        Text(text.uppercase(), color = LoginColors.MonoDim, fontSize = 10.sp, letterSpacing = 2.5.sp)
    }
}

/* -------------------------------------------------------------------- */
/*  LOGIN SCREEN                                                        */
/* -------------------------------------------------------------------- */
@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(LoginColors.Cyan.copy(alpha = 0.10f), LoginColors.Void),
                    center = Offset(0.5f, 0.0f)
                )
            )
            .background(LoginColors.Void)
    ) {
        LoginStarsBackground(modifier = Modifier.fillMaxSize())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 26.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))

            // Logo orb — echoes the .sun-core illustration
            Box(
                modifier = Modifier
                    .size(84.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(Color(0xFF2C2960), Color(0xFF12142C), Color(0xFF0A0B1C))
                        )
                    )
                    .border(1.dp, LoginColors.Line, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Lock, null, tint = LoginColors.Cyan, modifier = Modifier.size(32.dp))
            }

            Spacer(Modifier.height(24.dp))

            Text(
                "Welcome back",
                color = LoginColors.Ink,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Sign in to keep tracking live solar flares,\nCMEs and geomagnetic storms.",
                color = LoginColors.Dim,
                fontSize = 13.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(32.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                LoginSectionEyebrow("Account")
                LoginTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email address",
                    leadingIcon = Icons.Filled.Email,
                    keyboardType = KeyboardType.Email
                )
                LoginTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    leadingIcon = Icons.Filled.Lock,
                    isPassword = true
                )
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = {

                }) {
                    Text("Forgot password?", color = LoginColors.Cyan, fontSize = 12.sp)
                }
            }

            Spacer(Modifier.height(16.dp))

            LoginGradientButton(
                text = "Sign In",
                onClick = {
                    Log.d("User","$email and $password")
                          },
                enabled = email.isNotBlank() && password.isNotBlank()
            )

            Spacer(Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Don't have an account?", color = LoginColors.Dim, fontSize = 12.5.sp)
                TextButton(onClick = {
                    navController.navigate("register")
                }) {
                    Text("Create one", color = LoginColors.Cyan, fontSize = 12.5.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

