package com.orbit.auth.login

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orbit.R
import com.orbit.other.BlurEffect

@Preview
@Composable
fun Login(){

    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.Black)
    ){

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .size(120.dp)
                    .border(
                        width = 2.dp,
                        color = colorResource(R.color.app_blue),
                        shape = CircleShape
                    ),
            ){}

            Text(
                modifier = Modifier.padding(top = 30.dp),
                text = "ORBIT",
                fontSize = 25.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold)

            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = "Live telemetry from CME, storms\n& near-Earth object feeds\n",
                color = colorResource(R.color.text_color2),
                textAlign = TextAlign.Center,
                fontSize = 14.sp)

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

            Text(
                modifier = Modifier.padding(vertical = 10.dp),
                text = "Or",
                color = Color.White)

            Box(
                modifier = Modifier.fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = colorResource(R.color.app_blue),
                        shape = RoundedCornerShape(16.dp),
                    ),
                contentAlignment = Alignment.Center
            ){
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "CONTINUE AS GUEST",
                    fontSize = 14.sp,
                    color = Color.White,)
            }

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(top = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Box(
                        modifier = Modifier.padding(vertical = 15.dp,)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(color = colorResource(R.color.text_color2))
                    )

                    Row() {

                        Text(
                            modifier = Modifier.padding(end = 7.dp)
                                .clickable {
                                },
                            text = "Privacy Policy",
                            fontSize = 14.sp,
                            color = colorResource(R.color.text_color2)
                        )
                        Text(
                            modifier = Modifier.padding(end = 7.dp)
                                .clickable {
                                },
                            text = "Terms & condition",
                            fontSize = 14.sp,
                            color = colorResource(R.color.text_color2)
                        )
                    }
                    Text(
                        modifier = Modifier.padding(vertical = 10.dp),
                        text = "Version 1.0",
                        fontSize = 14.sp,
                        color = colorResource(R.color.text_color2)
                    )
                }
            }


        }
        BlurEffect()
    }

}