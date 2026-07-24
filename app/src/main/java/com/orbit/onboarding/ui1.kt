package com.orbit.onboarding
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.tooling.preview.Preview
//
//@Preview
//@Composable
//fun Onboarding1(){
//
//    Box(
//        modifier = Modifier.fillMaxSize()
//            .background(color = Color.Black)
//    ) {
//
//
//    }
//
//}
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.lerp as lerpDp
import kotlinx.coroutines.launch
import kotlin.math.abs

// ---------------- Design tokens (Orbit Watch theme) ----------------
private val Void = Color(0xFF060814)
private val Line = Color(0xFF1C2440)
private val Violet = Color(0xFF8B7BFF)
private val Cyan = Color(0xFF3FE0D0)
private val Ink = Color(0xFFEEF1FB)
private val Dim = Color(0xFF5B6690)

// ---------------- Page model ----------------
private data class OnboardingPage(
    val icon: ImageVector,
    val title: String,
    val subtitle: String
)

private val onboardingPages = listOf(
    OnboardingPage(
        icon = Icons.Default.Home,
        title = "Know the storm before it arrives",
        subtitle = "Orbit Watch pulls live solar flare, CME and geomagnetic storm data straight from NASA's DONKI feed — the moment it's issued."
    ),
    OnboardingPage(
        icon = Icons.Default.Home,
        title = "Every object, tracked in real time",
        subtitle = "Follow near-Earth asteroids, drifting icebergs and wildfires as they're logged by NASA's EONET and NEO feeds."
    )
)


// ---------------- Screen: 2 pages + dots + single advancing button ----------------
@Composable
fun OnboardingScreen(onFinished: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val scope = rememberCoroutineScope()

    val isLastPage by remember {
        derivedStateOf { pagerState.currentPage == onboardingPages.lastIndex }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Void)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            OnboardingPageContent(onboardingPages[page])
        }

        ProgressDotsIndicator(
            pageCount = onboardingPages.size,
            pagerState = pagerState,
            dotColor = Line,
            selectedDotColor = Cyan,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 22.dp)
        )

        // ---- single button: advances pages, becomes "Get Started" on the last one ----
        Button(
            onClick = {
                if (isLastPage) {
                    onFinished()
                } else {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Cyan),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp)
                .padding(bottom = 30.dp)
                .height(52.dp)
        ) {
            Text(
                text = if (isLastPage) "Get Started" else "Continue",
                color = Void,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )
        }
    }
}

// ---------------- Single onboarding page content ----------------
@Composable
private fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // simple gradient icon badge — swap for a custom illustration/SVG if you want
        // to match the fuller web mockups (sun flare / radar) exactly
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(listOf(Violet.copy(alpha = 0.35f), Color.Transparent))
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(84.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(listOf(Color(0xFF2C2960), Color(0xFF0A0B1C)))
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = page.icon,
                    contentDescription = null,
                    tint = Cyan,
                    modifier = Modifier.size(38.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = page.title,
            color = Ink,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 30.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = page.subtitle,
            color = Dim,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )
    }
}

// ---------------- Dot indicator (progress mode, same behavior discussed earlier) ----------------
@Composable
fun ProgressDotsIndicator(
    pageCount: Int,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    dotSize: Dp = 8.dp,
    dotSpacing: Dp = 6.dp,
    dotCornerRadius: Dp = 4.dp,
    dotColor: Color = Color(0xFFB0B4C2),
    selectedDotColor: Color = Color(0xFF2F6BFF),
    selectedDotWidthFactor: Float = 2.5f
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dotSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val current = pagerState.currentPage
        val offset = pagerState.currentPageOffsetFraction

        repeat(pageCount) { index ->
            val distance = abs((index - current) - offset)
            val fraction = (1f - distance).coerceIn(0f, 1f)

            val width = lerpDp(dotSize, dotSize * selectedDotWidthFactor, fraction)
            val color = lerp(dotColor, selectedDotColor, fraction)

            Box(
                modifier = Modifier
                    .height(dotSize)
                    .width(width)
                    .clip(RoundedCornerShape(dotCornerRadius))
                    .background(color)
            )
        }
    }
}
