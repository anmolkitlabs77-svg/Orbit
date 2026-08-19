package com.orbit.dashboard.weather

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.navigation.NavHostController
import com.orbit.R
import com.orbit.dashboard.weather.viewModel.weatherVM
import com.orbit.other.BlurEffect
import com.orbit.other.CommonText
import com.orbit.other.ErrorCompose

//@Composable
//fun Weather(navController: NavHostController) {
//
//    val viewModel : weatherVM = hiltViewModel()
//    val weatherData by viewModel.weather.collectAsState(initial = emptyList())
//
//
//    LaunchedEffect(Unit) {
//        weatherData.forEach{
//            Log.d("eirutyeiuteyirutyeitueyitu","item = ${it.messageID}")
//
//        }
//    }
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(color = Color.Black)
//    ) {
//        LazyColumn(
//            modifier = Modifier.padding(10.dp)
//        ) {
//
//            item {
//                Row() {
//                    val shape = RoundedCornerShape(18.dp)
//
//                    Box(
//                        modifier = Modifier
//                            .weight(1f)
//                            .padding(end = 5.dp)
//                            .clip(shape)
//                            .background(color = colorResource(R.color.bg_black))
//                            .border(
//                                2.dp,
//                                color = colorResource(R.color.app_blue),
//                                shape = shape
//
//                            )
//                    ) {
//                        Column(
//                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp)
//                        ) {
//                            Text(
//                                "Total alerts",
//                                fontSize = 15.sp,
//                                color = colorResource(R.color.text_color)
//                            )
//
//                            Text(
//                                modifier = Modifier.padding(vertical = 4.dp),
//                                text = "37",
//                                fontSize = 18.sp,
//                                color = Color.Red
//                            )
//
//                            Row(
//                                verticalAlignment = Alignment.CenterVertically,
//                            ) {
//                                Box(
//                                    modifier = Modifier
//                                        .size(10.dp)
//                                        .clip(CircleShape)
//                                        .background(Color.Yellow)
//                                )
//                                Text(
//                                    text = " 14 CMEs",
//                                    maxLines = 1,
//                                    fontSize = 14.sp,
//                                    color = colorResource(R.color.text2_blue)
//                                )
//                            }
//
//
//                        }
//                    }
//                    Box(
//                        modifier = Modifier
//                            .weight(1f)
//                            .padding(end = 5.dp)
//                            .clip(shape)
//                            .background(color = colorResource(R.color.bg_black))
//                            .border(
//                                2.dp,
//                                color = colorResource(R.color.app_blue),
//                                shape = shape
//                            )
//                    ) {
//                        Column(
//                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp)
//                        ) {
//                            Text(
//                                "Active events",
//                                fontSize = 15.sp,
//                                color = colorResource(R.color.text_color)
//                            )
//
//                            Text(
//                                modifier = Modifier.padding(vertical = 4.dp),
//                                text = "2",
//                                fontSize = 18.sp,
//                                color = Color.White
//                            )
//
//                            Row(
//                                verticalAlignment = Alignment.CenterVertically,
//                            ) {
//                                Box(
//                                    modifier = Modifier
//                                        .size(10.dp)
//                                        .clip(CircleShape)
//                                        .background(Color.Cyan)
//                                )
//                                Text(
//                                    " GST · RBE · IPS",
//                                    fontSize = 14.sp,
//                                    maxLines = 1,
//                                    color = colorResource(R.color.text2_blue)
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//
//            if(weatherData.size > 0) {
//                item {
//                    Text(
//                        modifier = Modifier.padding(vertical = 10.dp),
//                        text = "RECENT NOTIFICATIONS",
//                        color = colorResource(R.color.text_blue),
//                        fontSize = 16.sp,
//                    )
//                }
//
//                items(weatherData.size) { it ->
//
//                    Column(
//                        modifier = Modifier.padding(bottom = 10.dp)
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .clip(RoundedCornerShape(14.dp))
//                                .background(colorResource(R.color.cardbg))
//                        ) {
//                            // Left color stripe
//                            Box(
//                                modifier = Modifier
//                                    .width(4.dp)
//                                    .fillMaxHeight()
////                                .background(it.stripeColor)
//                            )
//
//                            Column(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(
//                                        start = 16.dp,
//                                        top = 16.dp,
//                                        end = 16.dp,
//                                        bottom = 16.dp
//                                    )
//                            ) {
//                                // Header row: title + badge
//                                Row(
//                                    modifier = Modifier.fillMaxWidth(),
//                                    horizontalArrangement = Arrangement.SpaceBetween,
//                                    verticalAlignment = Alignment.Top
//                                ) {
//                                    Text(
//                                        text = weatherData[it].messageID,
//                                        color = colorResource(R.color.title),
//                                        fontSize = 19.sp,
//                                        fontWeight = FontWeight.Medium,
//                                        modifier = Modifier.weight(1f)
//                                    )
//                                    Spacer(modifier = Modifier.width(8.dp))
////                                Badge(it.badgeText, it.badgeStyle)
//                                }
//
//                                Spacer(modifier = Modifier.height(16.dp))
//
//                                // Stats row
//                                Row(
//                                    modifier = Modifier.fillMaxWidth(),
//                                    horizontalArrangement = Arrangement.spacedBy(32.dp)
//                                ) {
////                                it.stats.forEach { stat ->
////                                    StatColumn(stat.label, stat.value)
//                                }
//                            }
//
//                            Spacer(modifier = Modifier.height(14.dp))
//
//                            // Description
//                            CommonText(
//                                name = " it.description",
//                                color = colorResource(R.color.body),
//                                fontSize = 14.sp,
//                                lineHeight = 20.sp,
//                                maxLines = 2,
//                                overflow = TextOverflow.Ellipsis
//                            )
//
//                            Spacer(modifier = Modifier.height(14.dp))
//
//                            // Footer
//                            Text(
//                                text = "it.footer",
//                                color = colorResource(R.color.footer),
//                                fontSize = 13.sp)
//                        }
//                    }
//                }
//            }
//            else {
//                item {
//                    ErrorCompose("somthing went wrong \ntry to update the api key",{})
//                }
//            }
//            }
//
//        BlurEffect()
//        }
//    }

@Composable
fun Weather(navController: NavHostController) {

    val viewModel: weatherVM = hiltViewModel()
    val weatherData by viewModel.weather.collectAsState(initial = emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {

            // -----------------------------------
            // TOP SUMMARY
            // -----------------------------------

            item {

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    val shape = RoundedCornerShape(18.dp)

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 5.dp)
                            .clip(shape)
                            .background(colorResource(R.color.bg_black))
                            .border(
                                2.dp,
                                colorResource(R.color.app_blue),
                                shape
                            )
                    ) {

                        Column(
                            modifier = Modifier.padding(
                                horizontal = 10.dp,
                                vertical = 15.dp
                            )
                        ) {

                            Text(
                                text = "Total alerts",
                                fontSize = 15.sp,
                                color = colorResource(R.color.text_color)
                            )

                            Text(
                                modifier = Modifier.padding(vertical = 4.dp),
                                text = weatherData.size.toString(),
                                fontSize = 18.sp,
                                color = Color.Red
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(Color.Yellow)
                                )

                                Text(
                                    text = " ${weatherData.count { it.messageType == "CME" }} CMEs",
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
                            .padding(start = 5.dp)
                            .clip(shape)
                            .background(colorResource(R.color.bg_black))
                            .border(
                                2.dp,
                                colorResource(R.color.app_blue),
                                shape
                            )
                    ) {

                        Column(
                            modifier = Modifier.padding(
                                horizontal = 10.dp,
                                vertical = 15.dp
                            )
                        ) {

                            Text(
                                text = "Active events",
                                fontSize = 15.sp,
                                color = colorResource(R.color.text_color)
                            )

                            Text(
                                modifier = Modifier.padding(vertical = 4.dp),
                                text = weatherData.size.toString(),
                                fontSize = 18.sp,
                                color = Color.White
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(Color.Cyan)
                                )

                                Text(
                                    text = " CME",
                                    fontSize = 14.sp,
                                    maxLines = 1,
                                    color = colorResource(R.color.text2_blue)
                                )
                            }
                        }
                    }
                }
            }

            // -----------------------------------
            // RECENT NOTIFICATIONS
            // -----------------------------------

            if (weatherData.isNotEmpty()) {

                item {

                    Text(
                        modifier = Modifier.padding(vertical = 10.dp),
                        text = "RECENT NOTIFICATIONS",
                        color = colorResource(R.color.text_blue),
                        fontSize = 16.sp
                    )
                }

                items(weatherData.size) { index ->

                    val item = weatherData[index]

                    // Parse the NASA messageBody
                    val cmeInfo = remember(item.messageBody) {
                        extractCmeInfo(item.messageBody)
                    }

                    Column(
                        modifier = Modifier.padding(bottom = 10.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(colorResource(R.color.cardbg))
                        ) {

                            // -----------------------------------
                            // LEFT STRIPE
                            // -----------------------------------

                            Box(
                                modifier = Modifier
                                    .width(4.dp)
                                    .height(220.dp)
                                    .background(
                                        colorResource(R.color.app_blue)
                                    )
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

                                // -----------------------------------
                                // HEADER
                                // -----------------------------------

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top
                                ) {

                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {

                                        Text(
                                            text = item.messageType,
                                            color = colorResource(R.color.title),
                                            fontSize = 19.sp,
                                            fontWeight = FontWeight.Medium
                                        )

                                        Spacer(
                                            modifier = Modifier.height(4.dp)
                                        )

                                        Text(
                                            text = item.messageID,
                                            color = colorResource(R.color.subtitle),
                                            fontSize = 12.sp
                                        )
                                    }

                                    // CME TYPE BADGE

                                    cmeInfo["type"]?.let { type ->

                                        Box(
                                            modifier = Modifier
                                                .clip(
                                                    RoundedCornerShape(20.dp)
                                                )
                                                .background(
                                                    colorResource(
                                                        R.color.app_blue
                                                    )
                                                )
                                                .padding(
                                                    horizontal = 10.dp,
                                                    vertical = 6.dp
                                                )
                                        ) {

                                            Text(
                                                text = type,
                                                color = Color.White,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }
                                }

                                Spacer(
                                    modifier = Modifier.height(16.dp)
                                )

                                // -----------------------------------
                                // CME STATS
                                // -----------------------------------

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {

                                    StatColumn(
                                        label = "Speed",
                                        value = cmeInfo["speed"] ?: "N/A"
                                    )

                                    StatColumn(
                                        label = "Half Angle",
                                        value = cmeInfo["halfAngle"] ?: "N/A"
                                    )

                                    StatColumn(
                                        label = "Direction",
                                        value = if (
                                            cmeInfo["longitude"] != null &&
                                            cmeInfo["latitude"] != null
                                        ) {
                                            "${cmeInfo["longitude"]}° / ${cmeInfo["latitude"]}°"
                                        } else {
                                            "N/A"
                                        }
                                    )
                                }

                                Spacer(
                                    modifier = Modifier.height(14.dp)
                                )

                                // -----------------------------------
                                // START TIME
                                // -----------------------------------

                                cmeInfo["startTime"]?.let { startTime ->

                                    StatColumn(
                                        label = "Start Time",
                                        value = startTime
                                    )
                                }

                                Spacer(
                                    modifier = Modifier.height(14.dp)
                                )

                                // -----------------------------------
                                // ACTIVITY ID
                                // -----------------------------------

                                cmeInfo["activityId"]?.let { activityId ->

                                    Text(
                                        text = "Activity ID: $activityId",
                                        color = colorResource(R.color.footer),
                                        fontSize = 12.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                Spacer(
                                    modifier = Modifier.height(10.dp)
                                )

                                // -----------------------------------
                                // SUMMARY
                                // -----------------------------------

                                CommonText(
                                    name = extractCmeSummary(
                                        item.messageBody
                                    ),
                                    color = colorResource(R.color.body),
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp,
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Spacer(
                                    modifier = Modifier.height(10.dp)
                                )

                                // -----------------------------------
                                // ISSUE TIME
                                // -----------------------------------

                                Text(
                                    text = "Issued: ${item.messageIssueTime}",
                                    color = colorResource(R.color.footer),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }

            } else {

                item {

                    ErrorCompose(
                        "Something went wrong\nTry to update the API key",
                        {}
                    )
                }
            }
        }

        BlurEffect()
    }
}


//@Composable
//private fun StatColumn(label: String, value: String) {
//    Column {
//        Text(text = label, color = colorResource(R.color.subtitle), fontSize = 13.sp)
//        Spacer(modifier = Modifier.height(4.dp))
//        Text(text = value, color = colorResource(R.color.value), fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
//    }
//}

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

fun extractCmeInfo(body: String): Map<String, String> {

    val result = mutableMapOf<String, String>()

    // C-type / S-type / O-type / R-type / ER-type
    Regex(
        """(?i)\b(ER|S|C|O|R)-type\s+CME"""
    )
        .find(body)
        ?.groupValues
        ?.getOrNull(1)
        ?.let {
            result["type"] = "$it-type"
        }

    // Speed
    Regex(
        """Estimated speed:\s*~?([\d.]+)\s*km/s"""
    )
        .find(body)
        ?.groupValues
        ?.getOrNull(1)
        ?.let {
            result["speed"] = "$it km/s"
        }

    // Half angle
    Regex(
        """Estimated opening half-angle:\s*([\d.]+)\s*deg"""
    )
        .find(body)
        ?.groupValues
        ?.getOrNull(1)
        ?.let {
            result["halfAngle"] = "$it°"
        }

    // Direction
    Regex(
        """Direction\s*\(lon\./lat\.\):\s*(-?\d+(?:\.\d+)?)\s*/\s*(-?\d+(?:\.\d+)?)"""
    )
        .find(body)
        ?.let {

            result["longitude"] = it.groupValues[1]
            result["latitude"] = it.groupValues[2]
        }

    // Start time
    Regex(
        """Start time of the event:\s*([^\s]+)"""
    )
        .find(body)
        ?.groupValues
        ?.getOrNull(1)
        ?.let {
            result["startTime"] = it
        }

    // Activity ID
    Regex(
        """Activity ID:\s*([^\s]+)"""
    )
        .find(body)
        ?.groupValues
        ?.getOrNull(1)
        ?.let {
            result["activityId"] = it
        }

    return result
}
fun extractCmeSummary(body: String): String {

    val summary = Regex(
        """## Summary:\s*\n\n(.*?)(?=\n\n## Notes:)""",
        RegexOption.DOT_MATCHES_ALL
    )
        .find(body)
        ?.groupValues
        ?.getOrNull(1)
        ?.trim()

    return summary ?: "No summary available."
}

@Composable
private fun StatColumn(
    label: String,
    value: String
) {
    Column(
        modifier = Modifier.width(100.dp)
    ) {

        Text(
            text = label,
            color = colorResource(R.color.subtitle),
            fontSize = 13.sp,
            maxLines = 1
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = value,
            color = colorResource(R.color.value),
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}