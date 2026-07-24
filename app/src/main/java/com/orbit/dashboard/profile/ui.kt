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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.orbit.R
import com.orbit.other.Colors

@Preview
@Composable
fun Profile(){

    val isGuest by remember { mutableStateOf(true) }
    var text by remember { mutableStateOf("") }
    val uriHandler = LocalUriHandler.current

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.Black)
    ){

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
                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = "Test User",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Text(
                    text = "anmol@yopmail.com",
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
                    Text(
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
                        text = "GUEST MODE",
                        color = colorResource(R.color.text2_blue),
                        fontSize = 14.sp
                        )
                }
                Text(
                    text = "Exploring as Guest",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp
                )

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 10.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    colorResource(R.color.green),
                                    colorResource(R.color.blue)
                                )
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                ){
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = 10.dp)
                    ) {
                        Icon(
                            modifier = Modifier.padding(end = 5.dp),
                            painter = painterResource(R.drawable.ic_google),
                            contentDescription = "sign in with google",
                            tint = Color.Unspecified
                        )
                        Text("Sign in with Google",
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold)

                    }
                }
            }

            title("API ACCESS",)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
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
                                    Brush.verticalGradient(listOf(Colors.ThumbGradientStart, Colors.ThumbGradientEnd))
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
                                color = colorResource(R.color.green),
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
                            uriHandler.openUri(it.item)
                        }
                    }
                    )
                }
            }

            title("FOLLOW ORBIT",)

            Row(
                modifier = Modifier.padding(vertical = 20.dp)
            ){
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            Brush.verticalGradient(listOf(Colors.ThumbGradientStart, Colors.ThumbGradientEnd))
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
                            Brush.verticalGradient(listOf(Colors.ThumbGradientStart, Colors.ThumbGradientEnd))
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
                            Brush.verticalGradient(listOf(Colors.ThumbGradientStart, Colors.ThumbGradientEnd))
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
                    Text(
                        modifier = Modifier.padding(vertical = 10.dp),
                        text = "Log Out",
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

                Text(
                    modifier = Modifier.padding(end = 7.dp)
                        .clickable{
                            uriHandler.openUri("https://google.com")
                        },
                    text = "Privacy Policy",
                    fontSize = 14.sp,
                    color = colorResource(R.color.green)
                    )
                Text(
                    modifier = Modifier.padding(end = 7.dp)
                        .clickable{
                            uriHandler.openUri("https://google.com")
                        },
                    text = "Terms & condition",
                    fontSize = 14.sp,
                    color = colorResource(R.color.green)
                )
            }
            Text(
                modifier = Modifier.padding(vertical = 10.dp),
                text = "Version 1.0",
                fontSize = 14.sp,
                color = colorResource(R.color.text_color2))

        }
    }
}

@Composable
fun title(title: String){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        Box(
            modifier = Modifier.width(20.dp)
                .height(3.dp)
                .background(color = colorResource(R.color.green))
        ) {

        }

        Text(
            modifier = Modifier.padding(start = 5.dp),
            text = title,
            fontSize = 14.sp,
            color = colorResource(R.color.text2_blue)
        )
    }
}
