package com.orbit.dashboard.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.orbit.R
import com.orbit.dashboard.weather.viewModel.weatherVM
import com.orbit.other.BlurEffect
import com.orbit.other.CommonText
import com.orbit.other.ErrorCompose

@Preview
@Composable
fun Weather() {

    val viewModel : weatherVM = hiltViewModel()
    val weatherData by viewModel.weather.collectAsState(initial = emptyList())


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
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp)
                        ) {
                            Text(
                                "Total alerts",
                                fontSize = 15.sp,
                                color = colorResource(R.color.text_color)
                            )

                            Text(
                                modifier = Modifier.padding(vertical = 4.dp),
                                text = "37",
                                fontSize = 18.sp,
                                color = Color.Red
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
                                    text = " 14 CMEs",
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
                                "Active events",
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
                                        .background(Color.Cyan)
                                )
                                Text(
                                    " GST · RBE · IPS",
                                    fontSize = 14.sp,
                                    maxLines = 1,
                                    color = colorResource(R.color.text2_blue)
                                )
                            }
                        }
                    }
                }
            }

            if(weatherData.size > 0 ) {
                item {
                    Text(
                        modifier = Modifier.padding(vertical = 10.dp),
                        text = "RECENT NOTIFICATIONS",
                        color = colorResource(R.color.text_blue),
                        fontSize = 16.sp,
                    )
                }

                items(weatherData.size) { it ->

                    Column(
                        modifier = Modifier.padding(bottom = 10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(colorResource(R.color.cardbg))
                        ) {
                            // Left color stripe
                            Box(
                                modifier = Modifier
                                    .width(4.dp)
                                    .fillMaxHeight()
//                                .background(it.stripeColor)
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 16.dp,
                                        top = 16.dp,
                                        end = 16.dp,
                                        bottom = 16.dp
                                    )
                            ) {
                                // Header row: title + badge
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        text = weatherData[it].messageId,
                                        color = colorResource(R.color.title),
                                        fontSize = 19.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
//                                Badge(it.badgeText, it.badgeStyle)
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                // Stats row
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(32.dp)
                                ) {
//                                it.stats.forEach { stat ->
//                                    StatColumn(stat.label, stat.value)
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Description
                            CommonText(
                                name = " it.description",
                                color = colorResource(R.color.body),
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            // Footer
                            Text(
                                text = "it.footer",
                                color = colorResource(R.color.footer),
                                fontSize = 13.sp)
                        }
                    }
                }
            }
            else {
                item {
                    ErrorCompose("somthing went wrong \ntry to update the api key",{})
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
        Text(text = value, color = colorResource(R.color.value), fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

//@Composable
//private fun Badge(text: String, style: BadgeStyle) {
//    when (style) {
//        BadgeStyle.C_TYPE -> BadgePill(text, Colors.CTypeBadgeBg, Colors.CTypeBadgeText)
//        BadgeStyle.REPORT -> BadgePill(text, Colors.ReportBadgeBg, Colors.ReportBadgeText)
//        BadgeStyle.NONE -> {}
//    }
//}

//@Composable
//private fun BadgePill(text: String, bg: Color, textColor: Color) {
//    Box(
//        modifier = Modifier
//            .clip(RoundedCornerShape(20.dp))
//            .background(bg)
//            .padding(horizontal = 12.dp, vertical = 6.dp)
//    ) {
//        Text(text = text, color = textColor, fontSize = 13.sp, fontWeight = FontWeight.Medium)
//    }
//}
