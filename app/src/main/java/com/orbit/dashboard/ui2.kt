package com.orbit.dashboard


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import kotlin.math.*
import kotlin.random.Random

private const val SE_DEG2RAD = (PI / 180.0).toFloat()
private fun degToRad(deg: Float) = deg * SE_DEG2RAD

private const val ORBIT_PERIOD = 12f

private class SeStar(val x: Float, val y: Float, val r: Float, val a: Float)

private fun makeSeStars(w: Float, h: Float) = List(180) {
    SeStar(
        x = Random.nextFloat() * w,
        y = Random.nextFloat() * h,
        r = Random.nextFloat() * 1.8f + 0.2f,
        a = Random.nextFloat() * 0.55f + 0.05f,
    )
}

private fun computeMoonAngleDeg(elapsed: Float): Float {
    val u = elapsed / ORBIT_PERIOD
    val v = 2f * u - 1f
    return 180f + 180f * v * v * v
}

private fun computeDarkness(elapsed: Float): Float {
    val angleDeg = computeMoonAngleDeg(elapsed)
    var diff = abs(angleDeg - 180f)
    if (diff > 180f) diff = 360f - diff
    return when {
        diff <= 3f  -> 1f
        diff >= 25f -> 0f
        else -> ((25f - diff) / 22f).coerceIn(0f, 1f)
    }
}

private class EclipseClock { var t = 0f }

// ── Draw helpers ──────────────────────────────────────────────────────────────

private fun DrawScope.drawSun(center: Offset, sunR: Float) {
    drawCircle(Color(0xFFFF7700).copy(alpha = 0.05f), sunR * 3.4f, center)
    drawCircle(Color(0xFFFFAA00).copy(alpha = 0.09f), sunR * 2.3f, center)
    drawCircle(Color(0xFFFFCC00).copy(alpha = 0.18f), sunR * 1.5f, center)
    drawCircle(Color(0xFFFFE066), sunR * 1.05f, center)
    drawCircle(Color(0xFFFFF7CC), sunR, center)
    drawCircle(Color.White.copy(alpha = 0.92f), sunR * 0.4f, center)
}

// Directional light rays from Sun toward Earth. Rays that pass close to the
// Sun–Earth axis get truncated at the Moon's silhouette as alignment nears,
// visually selling "the Moon is blocking the light."
private fun DrawScope.drawLightRays(sunPos: Offset, earthPos: Offset, moonPos: Offset, coverage: Float) {
    val dir    = Offset(earthPos.x - sunPos.x, earthPos.y - sunPos.y)
    val dist   = hypot(dir.x, dir.y)
    if (dist < 1f) return
    val fwd    = Offset(dir.x / dist, dir.y / dist)
    val perp   = Offset(-fwd.y, fwd.x)
    val blockHalfWidth = dist * (0.02f + 0.10f * coverage)
    val moonProj = (moonPos.x - sunPos.x) * fwd.x + (moonPos.y - sunPos.y) * fwd.y

    val rayCount = 7
    for (i in 0 until rayCount) {
        val f = (i - rayCount / 2) / (rayCount / 2f) // -1..1
        val offset = Offset(perp.x * f * dist * 0.18f, perp.y * f * dist * 0.18f)
        val start = Offset(sunPos.x + offset.x, sunPos.y + offset.y)
        val blocked = coverage > 0.03f && abs(f * dist * 0.18f) < blockHalfWidth
        val end = if (blocked) {
            Offset(sunPos.x + fwd.x * moonProj + offset.x, sunPos.y + fwd.y * moonProj + offset.y)
        } else {
            Offset(earthPos.x + offset.x, earthPos.y + offset.y)
        }
        drawLine(
            color       = Color(0xFFFFEFB0).copy(alpha = if (blocked) 0.10f else 0.16f),
            start       = start,
            end         = end,
            strokeWidth = 1.4f,
        )
    }
}

private fun DrawScope.drawEarth(center: Offset, earthR: Float, sunDir: Offset, coverage: Float) {
    val nightColor = Color(0xFF060A16)
    val dayColor   = lerp(Color(0xFF3E86D6), Color(0xFF13202E), coverage * 0.85f)

    drawCircle(nightColor, earthR, center)

    val litCenter = Offset(center.x - sunDir.x * earthR * 0.4f, center.y - sunDir.y * earthR * 0.4f)
    drawCircle(
        brush  = Brush.radialGradient(
            colors = listOf(dayColor, dayColor.copy(alpha = 0.55f), dayColor.copy(alpha = 0f)),
            center = litCenter,
            radius = earthR * 1.7f,
        ),
        radius = earthR,
        center = center,
    )

    // Faint atmospheric rim glow
    drawCircle(Color(0xFF6FB4FF).copy(alpha = 0.10f * (1f - coverage)), earthR * 1.12f, center, style = Stroke(earthR * 0.08f))

    // Specular highlight on the lit crescent
    drawCircle(
        Color.White.copy(alpha = 0.30f * (1f - coverage)), earthR * 0.14f,
        Offset(litCenter.x - earthR * 0.15f, litCenter.y - earthR * 0.15f),
    )
}

private fun DrawScope.drawUmbra(earthPos: Offset, earthR: Float, moonPos: Offset, sunDir: Offset, coverage: Float) {
    if (coverage <= 0.02f) return
    val shadowPoint = Offset(earthPos.x - sunDir.x * earthR, earthPos.y - sunDir.y * earthR)

    val toShadow = Offset(shadowPoint.x - moonPos.x, shadowPoint.y - moonPos.y)
    val len = hypot(toShadow.x, toShadow.y)
    if (len > 1f) {
        val dir  = Offset(toShadow.x / len, toShadow.y / len)
        val perp = Offset(-dir.y, dir.x)
        val nearHalfW = earthR * 0.16f
        val farHalfW  = earthR * 0.05f
        val cone = Path().apply {
            moveTo(moonPos.x + perp.x * nearHalfW, moonPos.y + perp.y * nearHalfW)
            lineTo(moonPos.x - perp.x * nearHalfW, moonPos.y - perp.y * nearHalfW)
            lineTo(shadowPoint.x - perp.x * farHalfW, shadowPoint.y - perp.y * farHalfW)
            lineTo(shadowPoint.x + perp.x * farHalfW, shadowPoint.y + perp.y * farHalfW)
            close()
        }
        drawPath(cone, Color(0xFF03030A).copy(alpha = 0.55f * coverage))
    }

    // The dark patch the shadow leaves on Earth's surface
    drawCircle(Color(0xFF03030A).copy(alpha = 0.8f * coverage), earthR * 0.22f, shadowPoint)
    drawCircle(Color(0xFF03030A).copy(alpha = 0.35f * coverage), earthR * 0.40f, shadowPoint)
}

private fun DrawScope.drawMoon(pos: Offset, r: Float) {
    drawCircle(Color(0xFFAAAAAA).copy(alpha = 0.10f), r * 3.5f, pos)
    drawCircle(Color(0xFF8E8E96), r, pos)
    drawCircle(
        Color.White.copy(alpha = 0.4f), r * 0.32f,
        Offset(pos.x - r * 0.28f, pos.y - r * 0.28f),
    )
}

// ── Main composable ───────────────────────────────────────────────────────────

@Composable
fun SolarEclipse() {
    var cw by remember { mutableFloatStateOf(0f) }
    var ch by remember { mutableFloatStateOf(0f) }

    val stars = remember { mutableListOf<SeStar>() }
    val trail = remember { ArrayDeque<Offset>(40) }

    val clock = remember { EclipseClock() }
    var frame by remember { mutableIntStateOf(0) }

    LaunchedEffect(cw, ch) {
        if (cw > 0f && stars.isEmpty()) stars += makeSeStars(cw, ch)
    }

    // Frame-synced physics loop — identical pattern to SolarSystem.
    LaunchedEffect(Unit) {
        var prev = 0L
        while (true) {
            withFrameNanos { t ->
                val dt = if (prev == 0L) 0.016f else ((t - prev) / 1e9f).coerceAtMost(0.05f)
                prev = t
                clock.t = (clock.t + dt) % ORBIT_PERIOD
                frame++
            }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color(0xFF020209))
    ) {
        Canvas(
            Modifier
                .fillMaxSize()
                .onSizeChanged { cw = it.width.toFloat(); ch = it.height.toFloat() }
        ) {
            frame.hashCode()
            if (cw == 0f) return@Canvas

            val minDim = minOf(cw, ch)
            val earthR = minDim * 0.15f
            val earthPos = Offset(cw / 2f + minDim * 0.10f, ch / 2f)
            val sunR = minDim * 0.095f
            val sunPos = Offset(earthPos.x - minDim * 0.62f, earthPos.y)

            val moonOrbitR = earthR * 2.5f
            val moonR = earthR * 0.27f
            val angleDeg = computeMoonAngleDeg(clock.t)
            val angleRad = degToRad(angleDeg)
            val moonPos = Offset(
                earthPos.x + moonOrbitR * cos(angleRad),
                earthPos.y + moonOrbitR * sin(angleRad),
            )
            trail.addFirst(moonPos)
            if (trail.size > 40) trail.removeLast()

            val coverage = computeDarkness(clock.t)
            val sunDist = hypot(earthPos.x - sunPos.x, earthPos.y - sunPos.y)
            val sunDir  = Offset((earthPos.x - sunPos.x) / sunDist, (earthPos.y - sunPos.y) / sunDist)

            // ── 1. Starfield ─────────────────────────────────────────────────
            stars.forEach { drawCircle(Color.White.copy(alpha = it.a), it.r, Offset(it.x, it.y)) }

            // ── 2. Sun ───────────────────────────────────────────────────────
            drawSun(sunPos, sunR)

            // ── 3. Light rays, interrupted near alignment ───────────────────
            drawLightRays(sunPos, earthPos, moonPos, coverage)

            // ── 4. Moon's orbit path + fading trail ──────────────────────────
            drawCircle(Color.White.copy(alpha = 0.07f), moonOrbitR, earthPos, style = Stroke(0.8f))
            trail.forEachIndexed { i, p ->
                val frac = 1f - i.toFloat() / trail.size
                drawCircle(Color(0xFFAAAAAA).copy(alpha = frac * 0.20f), moonR * frac * 0.6f, p)
            }

            // ── 5. Earth — lit hemisphere dims as the Moon aligns ────────────
            drawEarth(earthPos, earthR, sunDir, coverage)

            // ── 6. Umbra — shadow cone + darkened patch on Earth's face ─────
            drawUmbra(earthPos, earthR, moonPos, sunDir, coverage)

            // ── 7. Moon body ──────────────────────────────────────────────────
            drawMoon(moonPos, moonR)
        }

        // ── HUD ──────────────────────────────────────────────────────────────
        val coverageForHud by remember {
            derivedStateOf {
                frame.hashCode()
                computeDarkness(clock.t)
            }
        }
        val phaseLabel = when {
            coverageForHud >= 0.999f -> "TOTALITY"
            coverageForHud >= 0.4f   -> "PARTIAL ECLIPSE"
            coverageForHud >= 0.03f  -> "PENUMBRA"
            else                     -> "FULL DAYLIGHT"
        }

        Column(
            modifier             = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 56.dp),
            horizontalAlignment  = Alignment.CenterHorizontally,
        ) {
            Text(
                text          = "SOLAR  ECLIPSE",
                color         = Color(0xFFFFCC44),
                fontSize      = 20.sp,
                fontWeight    = FontWeight.Black,
                fontFamily    = FontFamily.Monospace,
                letterSpacing = 5.sp,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text          = "$phaseLabel  ·  ${(coverageForHud * 100).roundToInt()}% SHADOWED",
                color         = Color(0xFF9AA6BC),
                fontSize      = 10.sp,
                fontFamily    = FontFamily.Monospace,
                letterSpacing = 1.sp,
            )
        }
    }
}
