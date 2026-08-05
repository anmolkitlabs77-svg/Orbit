package com.orbit.dashboard.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.orbit.R
import com.orbit.other.BlurEffect
import com.orbit.other.CommonText
import com.orbit.other.GradientButton
import com.orbit.other.StarsBackground
import com.orbit.other.fieldText

@Composable
fun Profile(navController: NavHostController) {

    val isGuest by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
//    val uriHandler = LocalUriHandler.current
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.Black)
    ){
        StarsBackground()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
                .fillMaxSize()
                .verticalScroll(scrollState)

        ) {
            Card(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .size(100.dp)
                    .border(
                        width = 2.dp,
                        color = colorResource(R.color.app_blue),
                        shape = CircleShape
                    ),
                shape = CircleShape
            ) {
                AsyncImage(
                    model = "https://www.vecteezy.com/photo/68964462-vibrant-hummingbird-feeding-on-stunning-red-flower",
                    contentDescription = "Profile Image",
//                    placeholder = painterResource(R.drawable.ic_profile_placeholder),
//                    error = painterResource(R.drawable.ic_profile_placeholder),
//                    fallback = painterResource(R.drawable.ic_profile_placeholder),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            if(!isGuest) {
                CommonText(
                    modifier = Modifier.padding(top = 10.dp),
                    name = "Test User",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                CommonText(
                    name = "anmol@yopmail.com",
                    color = colorResource(R.color.text_color2),
                    fontSize = 15.sp
                )
            }
            else {
                Box(
                    modifier = Modifier
                    .padding(top = 10.dp, bottom = 5.dp)
                    .clip(RoundedCornerShape(50))
                    .background(colorResource(R.color.black))
                    .border(
                        1.dp,
                        color = colorResource(R.color.app_blue),
                        shape = RoundedCornerShape(50)
                    )
                ) {
                    CommonText(
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
                        name = "GUEST MODE",
                        color = colorResource(R.color.text2_blue),
                        fontSize = 14.sp
                        )
                }
                CommonText(
                    name = "Exploring as Guest",
                    modifier = Modifier.padding(bottom = 10.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp
                )

                GradientButton(
                    text = "Register with Orbit",
                    onClick = {

                    },
                    enabled = true
                )
            }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        fieldText("api access", modifer = Modifier.padding(top = 20.dp))
                    }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .border(
                        2.dp,
                        color = colorResource(R.color.app_blue),
                        shape = RoundedCornerShape(14.dp)
                    )

            ) {
                Column() {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(14.dp)

                    ) {
                        OutlinedTextField(
                            value = text,
                            onValueChange = { text = it },
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .weight(1f)
                                .height(56.dp),
                            shape = RoundedCornerShape(14.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = colorResource(R.color.app_blue),
                                unfocusedBorderColor = colorResource(R.color.app_blue),
                                cursorColor = Color.White,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            )
                        )
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(
                                    Brush.verticalGradient(listOf(colorResource(R.color.thumb1), colorResource(R.color.thumb2)))
                                )
                                .border(1.dp,
                                    color = colorResource(R.color.app_blue),
                                    RoundedCornerShape(14.dp))
                        )
                    }
                    val annotatedText = buildAnnotatedString {
                        // Normal text
                        withStyle(
                            SpanStyle(
                                color = colorResource(R.color.text_color2)
                            )
                        ) {
                            append("Update your API key to keep your data feeds active.Need a new one? ")
                        }

                        // Clickable text
                        pushStringAnnotation(
                            tag = "LINK",
                            annotation = "https://google.com"
                        )

                        withStyle(
                            SpanStyle(
                                color = colorResource(R.color.cyan),
                            )
                        ) {
                            append("Get your key here ->")
                        }

                        pop()
                    }

                    ClickableText(
                            modifier = Modifier.padding(start = 14.dp, bottom = 14.dp),
                    text = annotatedText,
                    style = TextStyle(
                        fontSize = 13.sp
                    ),
                    onClick = { offset ->
                        annotatedText.getStringAnnotations(
                            tag = "LINK",
                            start = offset,
                            end = offset
                        ).firstOrNull()?.let {
//                            uriHandler.openUri(it.item)
                        }
                    }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                fieldText("FOLLOW ORBIT")
            }


            Row(
                modifier = Modifier.padding(top = 10.dp, bottom = 20.dp)
            ){
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            Brush.verticalGradient(listOf(colorResource(R.color.thumb1), colorResource(R.color.thumb2)))
                        )
                        .border(1.dp,
                            color = colorResource(R.color.app_blue),
                            RoundedCornerShape(14.dp))
                )
                Box(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .size(56.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            Brush.verticalGradient(listOf(colorResource(R.color.thumb1), colorResource(R.color.thumb2)))
                        )
                        .border(1.dp,
                            color = colorResource(R.color.app_blue),
                            RoundedCornerShape(14.dp))
                )
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            Brush.verticalGradient(listOf(colorResource(R.color.thumb1), colorResource(R.color.thumb2)))
                        )
                        .border(1.dp,
                            color = colorResource(R.color.app_blue),
                            RoundedCornerShape(14.dp))
                )
            }


            if(!isGuest) {
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))

                        .border(
                            1.dp,
                            color = colorResource(R.color.red),
                            shape = RoundedCornerShape(16.dp)
                        ),

                    contentAlignment = Alignment.Center
                ) {
                    CommonText(
                        modifier = Modifier.padding(vertical = 10.dp),
                        name = "Log Out",
                        color = colorResource(R.color.red)
                    )
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 15.dp, horizontal = 10.dp)
                    .height(1.dp)
                    .background(color=colorResource(R.color.text_color2))
            )

            Row() {

                CommonText(
                    modifier = Modifier.padding(end = 7.dp)
                        .clickable{
                            navController.navigate("webView")

//                            uriHandler.openUri("https://google.com")
                        },
                    name = "Privacy Policy",
                    fontSize = 13.sp,
                    color = colorResource(R.color.dim)
                    )
                CommonText(
                    modifier = Modifier.padding(end = 7.dp)
                        .clickable{
//                            uriHandler.openUri("https://google.com")
                        },
                    name = "Terms & condition",
                    fontSize = 13.sp,
                    color = colorResource(R.color.dim)
                )
            }
            CommonText(
                modifier = Modifier.padding(bottom = 10.dp),
                name = "Version 1.0",
                fontSize = 13.sp,
                color = colorResource(R.color.dim))

        }
        BlurEffect()
    }
}

