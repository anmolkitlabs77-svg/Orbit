package com.orbit.dashboard.events

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.orbit.R
import com.orbit.dashboard.events.viewModel.eventsVM
import com.orbit.other.BlurEffect

@Preview
@Composable
fun Events(navController: NavHostController) {

    val viewModel : eventsVM = hiltViewModel()
    val events by viewModel.events.collectAsState(initial = emptyList())

    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.Black)
    ){
        LazyColumn(
            modifier = Modifier.padding(10.dp)
        ) {
            item {
                Row() {
                    val shape = RoundedCornerShape(18.dp)

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
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier.padding(vertical = 4.dp),
                                text = "3",
                                fontSize = 18.sp,
                                color = Color.Yellow
                            )

                            Text(
                                "Storms",
                                maxLines = 1,
                                fontSize = 14.sp,
                                color = colorResource(R.color.text2_blue)
                            )
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
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier.padding(vertical = 4.dp),
                                text = "5",
                                fontSize = 18.sp,
                                color = Color.Red
                            )

                            Text(
                                "Wildfires",
                                maxLines = 1,
                                fontSize = 14.sp,
                                color = colorResource(R.color.text2_blue)
                            )
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
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier.padding(vertical = 4.dp),
                                text = "5",
                                fontSize = 18.sp,
                                color = colorResource(R.color.ice_blue)
                            )

                            Text(
                                "Icebergs",
                                maxLines = 1,
                                fontSize = 14.sp,
                                color = colorResource(R.color.text2_blue)
                            )
                        }
                    }
                }
            }
            item {
                Text(
                    "SEVERS STORMS",
                    color = colorResource(R.color.text_blue),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
                )
            }

            items(events.size){it->
                Column(
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(colorResource(R.color.cardbg))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Thumbnail
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(
                                    Brush.verticalGradient(listOf(colorResource(R.color.thumborder), colorResource(R.color.thumb2)))
                                )
                                .border(1.dp, colorResource(R.color.thumborder).copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                        )

                        Spacer(modifier = Modifier.width(14.dp))

                        // Text block
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = events[it].id,
                                color = colorResource(R.color.title),
                                fontSize = 17.sp,
                                maxLines = 1
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "storm.longitude · storm.latitude · storm.date}",
                                color = colorResource(R.color.subtitle),
                                fontSize = 13.sp,
                                maxLines = 1
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        StatusBadge()
                    }
                }

            }
            item {
                Text(
                    "WILDFIRES",
                    color = colorResource(R.color.text_blue),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
                )
            }

            items(events.size){it->
                Column(
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(colorResource(R.color.cardbg))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Thumbnail
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(
                                    Brush.verticalGradient(listOf(colorResource(R.color.thumb1), colorResource(R.color.thumb2)))
                                )
                                .border(1.dp, colorResource(R.color.thumborder).copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                        )

                        Spacer(modifier = Modifier.width(14.dp))

                        // Text block
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = events[it].id,
                                color = colorResource(R.color.title),
                                fontSize = 17.sp,
                                maxLines = 1
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "storm.longitude · storm.latitude · storm.date}",
                                color = colorResource(R.color.subtitle),
                                fontSize = 13.sp,
                                maxLines = 1
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        StatusBadge()
                    }
                }
            }
        }
        BlurEffect()
    }
}


@Composable
private fun StatusBadge() {
    BadgePill("Active", colorResource(R.color.activebadge), colorResource(R.color.activebadgeborder), colorResource(R.color.activebadgetext))}
@Composable
private fun BadgePill(text: String, bg: Color, border: Color, textColor: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bg)
            .border(1.dp, border, RoundedCornerShape(20.dp))
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Text(text = text, color = textColor, fontSize = 13.sp, )
    }
}