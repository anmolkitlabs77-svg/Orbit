package com.orbit.prelogin.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.orbit.R
import com.orbit.other.CommonText
import com.orbit.other.GradientButton
import com.orbit.other.StarsBackground
import androidx.compose.ui.unit.lerp as lerpDp
import kotlinx.coroutines.launch
import kotlin.math.abs

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


@Composable
fun OnboardingScreen(navController: NavHostController) {
    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val scope = rememberCoroutineScope()

    val isLastPage by remember {
        derivedStateOf { pagerState.currentPage == onboardingPages.lastIndex }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        StarsBackground()
        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(horizontal = 26.dp, vertical = 40.dp),
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
                dotColor = colorResource(R.color.line),
                selectedDotColor = colorResource(R.color.cyan),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 22.dp)
            )

            GradientButton(
                text = if (isLastPage) "Get Started" else "Continue",
                onClick = {
                    if (isLastPage) {
                        navController.navigate("login") {
                            popUpTo("splash") {
                                inclusive = true
                            }
                        }
                    } else {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                enabled = true
            )
        }
    }
}

@Composable
private fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(listOf(colorResource(R.color.violet).copy(alpha = 0.35f), Color.Transparent))
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
                    tint = colorResource(R.color.cyan),
                    modifier = Modifier.size(38.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        CommonText(
            name = page.title,
            color = colorResource(R.color.ink),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 30.sp)

        Spacer(modifier = Modifier.height(12.dp))

        CommonText(
            name = page.subtitle,
            color = colorResource(R.color.text_color2),
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )
    }
}

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
