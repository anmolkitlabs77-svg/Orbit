package com.orbit.other


import com.orbit.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topAppBar(title: String, subtitle: String){

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.black)),

        actions = {
            Box(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(colorResource(R.color.black))
            )
        },

        title = {
            Column(
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(title,
                    fontSize = 18.sp,
                    color = Color.White,
                    fontFamily =  FontFamily(Font(R.font.inter_medium),),
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false))
                    )

                Text(subtitle,
                    fontSize = 11.sp,
                    color = colorResource(R.color.text2_blue),
                    fontFamily =  FontFamily(Font(R.font.inter_regular),),
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        ))
                    )
            }
        }
        )
}

@Composable
fun ErrorCompose(message: String,onClick : ()->Unit){

    Box(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 60.dp, start = 10.dp, end = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Text(
                text = message,
                textAlign = TextAlign.Center,
                color = colorResource(R.color.text_blue),
                fontSize = 16.sp,
            )

            Box(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .background(color = colorResource(R.color.bg_black))
                    .clip(RoundedCornerShape(5.dp))
                    .clickable(onClick = onClick)
                    .border(
                        2.dp,
                        color = colorResource(R.color.app_blue),
                        shape = RoundedCornerShape(5.dp)

                    )
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    text = "settings",
                    fontSize = 16.sp,
                    color = colorResource(R.color.text_color)
                )
            }
        }

    }


}