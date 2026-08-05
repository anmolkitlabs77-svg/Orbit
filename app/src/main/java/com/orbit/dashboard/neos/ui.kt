package com.orbit.dashboard.neos

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.orbit.R
import com.orbit.dashboard.neos.viewModel.neosVM
import com.orbit.other.BlurEffect
import com.orbit.other.ErrorCompose


@Preview
@Composable
fun Neos(){

    val viewModel : neosVM = hiltViewModel()
    val neosData by viewModel.neos.collectAsState(initial = emptyList())
    val shape = RoundedCornerShape(18.dp)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        LazyColumn(
            modifier = Modifier.padding(10.dp)
        ) {
            item {
                Row() {

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 5.dp)
                            .clip(shape)
                            .background(color = colorResource(R.color.bg_black))
                            .border(
                                2.dp,
                                color = colorResource(R.color.app_blue),
                                shape = shape

                            )
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp)
                        ) {
                            Text(
                                "Total today",
                                fontSize = 15.sp,
                                color = colorResource(R.color.text_color)
                            )

                            Text(
                                modifier = Modifier.padding(vertical = 4.dp),
                                text = "8",
                                fontSize = 18.sp,
                                color = Color.White
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(Color.Red)
                                )
                                Text(
                                    " 1 hazardous",
                                    maxLines = 1,
                                    fontSize = 14.sp,
                                    color = colorResource(R.color.text2_blue)
                                )
                            }


                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 5.dp)
                            .clip(shape)
                            .background(color = colorResource(R.color.bg_black))
                            .border(
                                2.dp,
                                color = colorResource(R.color.app_blue),
                                shape = shape
                            )
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp)
                        ) {
                            Text(
                                "Sentry watch",
                                fontSize = 15.sp,
                                color = colorResource(R.color.text_color)
                            )

                            Text(
                                modifier = Modifier.padding(vertical = 4.dp),
                                text = "2",
                                fontSize = 18.sp,
                                color = Color.White
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(Color.Yellow)
                                )
                                Text(
                                    " monitored",
                                    fontSize = 14.sp,
                                    maxLines = 1,
                                    color = colorResource(R.color.text2_blue)
                                )
                            }


                        }
                    }
                }
            }

            if(neosData.size > 0) {
                item {
                    Text(
                        modifier = Modifier.padding(vertical = 10.dp),
                        text = "CLOSE APPROACHES",
                        color = colorResource(R.color.text_blue),
                        fontSize = 16.sp,
                    )
                }
            }
            if(neosData.size > 0){

//                items(neosData.size){it->
                items(
                    items = neosData,
                    key = { it.date }
                ) { it ->
                Row(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(colorResource(R.color.cardbg))
                ) {
                    // Left color stripe
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .fillMaxHeight()
                            .background(colorResource(R.color.text_blue))
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
                    ) {
                        // Header row: name + badge
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = it.title,
                                color = colorResource(R.color.value),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Badge(it.status)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Stats row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(32.dp)
                        ) {
                            StatColumn("Miss distance", it.distance)
                            StatColumn("Velocity", it.velocity)
                            StatColumn("Diameter", it.diameter)
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = it.approachDate,
                            color = colorResource(R.color.footer),
                            fontSize = 13.sp
                        )
                    }
                }
            }

            }
            else {

                item {
                    ErrorCompose("somthing went wrong \ntry to update the api key",{

                    })
                }
            }

            }

        BlurEffect()
        }
    }

@Composable
private fun StatColumn(label: String, value: String) {
    Column {
        Text(text = label, color = colorResource(R.color.subtitle), fontSize = 13.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, color = colorResource(R.color.value), fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun Badge(type: String) {
    when (type) {
            "Hazardous" -> BadgePill(
            text = "⚠ Hazardous",
            bg = colorResource(R.color.hazard),
            textColor = colorResource(R.color.hazardtext)
        )
        "Sentry" -> BadgePill(
            text = "Sentry",
            bg = colorResource(R.color.sentry),
            textColor = colorResource(R.color.sentrytext)
        )
        else -> {}
    }
}
@Composable
private fun BadgePill(text: String, bg: Color, textColor: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bg)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text = text, color = textColor, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

