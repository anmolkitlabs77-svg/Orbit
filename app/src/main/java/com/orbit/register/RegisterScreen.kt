package com.orbitwatch.ui.auth

import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
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
import com.orbit.register.model.RegisterRequest
import com.orbit.register.viewModel.registerVM
import kotlin.random.Random

/* -------------------------------------------------------------------- */
/*  Palette — lifted from the Orbit Watch HTML design tokens             */
/* -------------------------------------------------------------------- */
private object RegisterColors {
    val Void = Color(0xFF060814)
    val Line = Color(0xFF1C2440)
    val Violet = Color(0xFF8B7BFF)
    val Cyan = Color(0xFF3FE0D0)
    val Danger = Color(0xFFF0665F)
    val Ink = Color(0xFFEEF1FB)
    val Dim = Color(0xFF5B6690)
    val MonoDim = Color(0xFF46517A)
    val FieldBg = Color(0xFF080A15)

    val cyanVioletGradient = Brush.linearGradient(listOf(Cyan, Violet))
}

@Preview
@Composable
private fun RegisterStarsBackground(modifier: Modifier = Modifier, starCount: Int = 40) {
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
private fun RegisterTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector,
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
        placeholder = { Text(label, color = RegisterColors.Dim, fontSize = 13.sp) },
        leadingIcon = { Icon(leadingIcon, null, tint = RegisterColors.Cyan) },
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { visible = !visible }) {
                    Icon(
                        if (visible) Icons.Filled.Person else Icons.Filled.Favorite,
                        contentDescription = null,
                        tint = RegisterColors.Dim
                    )
                }
            }
        },
        visualTransformation = if (isPassword && !visible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = RegisterColors.FieldBg,
            unfocusedContainerColor = RegisterColors.FieldBg,
            disabledContainerColor = RegisterColors.FieldBg,
            focusedBorderColor = RegisterColors.Cyan,
            unfocusedBorderColor = RegisterColors.Line,
            focusedTextColor = RegisterColors.Ink,
            unfocusedTextColor = RegisterColors.Ink,
            cursorColor = RegisterColors.Cyan
        )
    )
}

@Composable
private fun RegisterGradientButton(text: String, onClick: () -> Unit, enabled: Boolean = true) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(if (enabled) RegisterColors.cyanVioletGradient else SolidColor(RegisterColors.Line)),
        contentAlignment = Alignment.Center
    ) {
        TextButton(onClick = onClick, enabled = enabled, modifier = Modifier.fillMaxSize()) {
            Text(
                text,
                color = if (enabled) RegisterColors.Void else RegisterColors.Dim,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun RegisterSectionEyebrow(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier
                .width(12.dp)
                .height(1.dp)
                .background(RegisterColors.Cyan)
        )
        Spacer(Modifier.width(8.dp))
        Text(text.uppercase(), color = RegisterColors.MonoDim, fontSize = 10.sp, letterSpacing = 2.5.sp)
    }
}

/** Title text with the cyan→violet gradient word, matching .headline .accent */
@Composable
private fun registerGradientTitle() = buildAnnotatedString {
    append("Join the ")
    withStyle(style = SpanStyle(brush = RegisterColors.cyanVioletGradient)) {
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
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val activity = LocalActivity.current

    val viewModel : registerVM = hiltViewModel()





    val passwordsMatch = password.isNotBlank() && password == confirmPassword

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(RegisterColors.Violet.copy(alpha = 0.10f), RegisterColors.Void),
                    center = Offset(0.9f, 0.1f)
                )
            )
            .background(RegisterColors.Void)
    ) {
        RegisterStarsBackground(modifier = Modifier.fillMaxSize())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 26.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(16.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RegisterSectionEyebrow("Orientation · Sign up")
                TextButton(onClick = {

                }) {
                    Text("Sign in instead", color = RegisterColors.Dim, fontSize = 10.5.sp)
                }
            }

            Spacer(Modifier.height(28.dp))

            Text(
                registerGradientTitle(),
                color = RegisterColors.Ink,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 30.sp,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(10.dp))
            Text(
                "Create an account to get live NASA DONKI\nalerts pushed straight to your device.",
                color = RegisterColors.Dim,
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
                RegisterTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Full name",
                    leadingIcon = Icons.Filled.Person
                )
                RegisterTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email address",
                    leadingIcon = Icons.Filled.Email,
                    keyboardType = KeyboardType.Email
                )
                RegisterTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    leadingIcon = Icons.Filled.Lock,
                    isPassword = true
                )
                RegisterTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = "Confirm password",
                    leadingIcon = Icons.Filled.Lock,
                    isPassword = true
                )
                if (confirmPassword.isNotBlank() && !passwordsMatch) {
                    Text("Passwords don't match", color = RegisterColors.Danger, fontSize = 11.sp)
                }
            }

            Spacer(Modifier.height(20.dp))

            RegisterGradientButton(
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

                    Log.d("User","$name and $email and $password")
                },
                enabled = name.isNotBlank() && email.isNotBlank() && passwordsMatch
            )

            Spacer(Modifier.height(14.dp))

            Text(
                "By continuing you agree to Orbit Watch's Privacy Policy and Terms of Service.",
                color = RegisterColors.MonoDim,
                fontSize = 10.5.sp,
                lineHeight = 16.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Already have an account?", color = RegisterColors.Dim, fontSize = 12.5.sp)
                TextButton(onClick = {
                    navController.navigate("login")
                }) {
                    Text("Sign in", color = RegisterColors.Cyan, fontSize = 12.5.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
