package com.orbit.dashboard
//SolarEclipse

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import kotlin.math.*
import kotlin.random.Random

private const val SS_TWO_PI = (2.0 * PI).toFloat()

// ── Starfield ─────────────────────────────────────────────────────────────────

private class SsStar(val x: Float, val y: Float, val r: Float, val a: Float)

private fun makeSsStars(w: Float, h: Float) = List(200) {
    SsStar(
        x = Random.nextFloat() * w,
        y = Random.nextFloat() * h,
        r = Random.nextFloat() * 1.8f + 0.2f,
        a = Random.nextFloat() * 0.55f + 0.05f,
    )
}

// ── Planet definitions ────────────────────────────────────────────────────────
//
// orbitFrac  : semi-major axis as fraction of usable radius (minDim/2 * 0.93)
// ecc        : orbital eccentricity from real solar-system data
// periodSec  : animation loop period in real-time seconds; ratio follows Kepler III (T² ∝ a³)
// bodyRFrac  : visual radius as fraction of minDim (compressed for legibility)
// moonOrbitFrac / moonPeriodSec : Earth's Moon parameters

private data class PlanetDef(
    val name: String,
    val orbitFrac: Float,
    val ecc: Float,
    val periodSec: Float,
    val bodyRFrac: Float,
    val color: Color,
    val glowColor: Color,
    val hasRings: Boolean = false,
    val moonOrbitFrac: Float = 0f,
    val moonPeriodSec: Float = 0f,
)

private val PLANET_DEFS = listOf(
    PlanetDef("MERCURY", 0.130f, 0.206f,    2.4f, 0.012f, Color(0xFFB5B5B5), Color(0xFFD8D8D8)),
    PlanetDef("VENUS",   0.195f, 0.007f,    6.2f, 0.019f, Color(0xFFE8C86A), Color(0xFFFFE07A)),
    PlanetDef("EARTH",   0.268f, 0.017f,   10.0f, 0.021f, Color(0xFF4A90D9), Color(0xFF7EC8FF),
        moonOrbitFrac = 0.048f, moonPeriodSec = 1.05f),
    PlanetDef("MARS",    0.352f, 0.093f,   18.8f, 0.015f, Color(0xFFD45F3C), Color(0xFFFF7755)),
    PlanetDef("JUPITER", 0.485f, 0.049f,   95.0f, 0.040f, Color(0xFFD4956A), Color(0xFFFFBB88)),
    PlanetDef("SATURN",  0.615f, 0.056f,  236.0f, 0.032f, Color(0xFFE8D498), Color(0xFFFFF0BB), hasRings = true),
    PlanetDef("URANUS",  0.745f, 0.047f,  672.0f, 0.025f, Color(0xFF7DE8E8), Color(0xFFAAFFFF)),
    PlanetDef("NEPTUNE", 0.882f, 0.010f, 1318.0f, 0.023f, Color(0xFF4169E1), Color(0xFF6699FF)),
)

// ── Mutable planet state ──────────────────────────────────────────────────────

private class PlanetState(val def: PlanetDef, initAngle: Float) {
    var angle     = initAngle
    var moonAngle = Random.nextFloat() * SS_TWO_PI
    val trail     = ArrayDeque<Offset>(46)

    fun pos(cx: Float, cy: Float, scale: Float): Offset {
        val a = def.orbitFrac * scale
        val b = a * sqrt(1f - def.ecc * def.ecc)
        return Offset(cx + a * cos(angle), cy + b * sin(angle))
    }

    fun moonPos(cx: Float, cy: Float, scale: Float): Offset {
        val p  = pos(cx, cy, scale)
        val mr = def.moonOrbitFrac * scale
        return Offset(p.x + mr * cos(moonAngle), p.y + mr * sin(moonAngle))
    }

    fun step(dt: Float) {
        angle     = (angle     + SS_TWO_PI / def.periodSec * dt) % SS_TWO_PI
        if (def.moonOrbitFrac > 0f)
            moonAngle = (moonAngle + SS_TWO_PI / def.moonPeriodSec * dt) % SS_TWO_PI
    }
}

// ── Draw helpers ──────────────────────────────────────────────────────────────

// Saturn's rings drawn in two halves to achieve a 3-D over/under effect.
// front=false → bottom arc drawn BEFORE planet body (visually behind it).
// front=true  → top  arc drawn AFTER  planet body (visually in front of it).
private fun DrawScope.drawSaturnRings(pos: Offset, r: Float, front: Boolean) {
    val startAngle = if (front) 180f else 0f
    listOf(
        Triple(2.30f, 0.50f, 0.48f),
        Triple(1.90f, 0.43f, 0.36f),
        Triple(1.58f, 0.37f, 0.24f),
    ).forEach { (hScale, vScale, alpha) ->
        val rA = r * hScale
        val rB = r * vScale
        drawArc(
            color      = Color(0xFFE8D498).copy(alpha = alpha),
            startAngle = startAngle,
            sweepAngle = 180f,
            useCenter  = false,
            topLeft    = Offset(pos.x - rA, pos.y - rB),
            size       = Size(rA * 2f, rB * 2f),
            style      = Stroke(r * 0.60f, cap = StrokeCap.Round),
        )
    }
}

private fun DrawScope.drawPlanetBody(p: PlanetState, pos: Offset, minDim: Float) {
    val r    = p.def.bodyRFrac * minDim
    val glow = p.def.glowColor
    drawCircle(glow.copy(alpha = 0.04f), r * 8f,   pos)
    drawCircle(glow.copy(alpha = 0.09f), r * 4.5f, pos)
    drawCircle(glow.copy(alpha = 0.18f), r * 2.4f, pos)
    drawCircle(p.def.color,               r,        pos)
    // Specular highlight offset toward upper-left
    drawCircle(Color.White.copy(alpha = 0.42f), r * 0.36f,
        Offset(pos.x - r * 0.27f, pos.y - r * 0.27f))
}

// ── Main composable ───────────────────────────────────────────────────────────

@Composable
fun SolarSystem() {
    var cw by remember { mutableFloatStateOf(0f) }
    var ch by remember { mutableFloatStateOf(0f) }

    val stars   = remember { mutableListOf<SsStar>() }
    val planets = remember {
        PLANET_DEFS.mapIndexed { i, def ->
            // Spread initial angles evenly so no two planets start in the same position
            PlanetState(def, i * (SS_TWO_PI / PLANET_DEFS.size))
        }
    }
    var frame by remember { mutableIntStateOf(0) }

    val textMeasurer = rememberTextMeasurer()

    // Pre-measure planet name labels once; text never changes, only draw position does
    val labelLayouts = remember(textMeasurer) {
        PLANET_DEFS.map { def ->
            textMeasurer.measure(
                def.name,
                TextStyle(fontSize = 7.sp, fontFamily = FontFamily.Monospace, letterSpacing = 0.8.sp)
            )
        }
    }

    // Sun corona breathing pulse
    val sunPulse by rememberInfiniteTransition(label = "sun").animateFloat(
        initialValue = 0.88f, targetValue = 1.20f,
        animationSpec = infiniteRepeatable(
            tween(2600, easing = FastOutSlowInEasing),
            RepeatMode.Reverse,
        ),
        label = "sunPulse",
    )

    LaunchedEffect(cw, ch) {
        if (cw > 0f && stars.isEmpty()) stars += makeSsStars(cw, ch)
    }

    // Frame-synced physics loop — identical pattern to GravityNebula
    LaunchedEffect(Unit) {
        var prev = 0L
        while (true) {
            withFrameNanos { t ->
                val dt = if (prev == 0L) 0.016f else ((t - prev) / 1e9f).coerceAtMost(0.05f)
                prev = t
                if (cw > 0f) {
                    val cx    = cw / 2f
                    val cy    = ch / 2f
                    val scale = minOf(cw, ch) / 2f * 0.93f
                    planets.forEach { p ->
                        p.step(dt)
                        val pos = p.pos(cx, cy, scale)
                        p.trail.addFirst(pos)
                        if (p.trail.size > 46) p.trail.removeLast()
                    }
                    frame++
                }
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

            val cx     = cw / 2f
            val cy     = ch / 2f
            val scale  = minOf(cw, ch) / 2f * 0.93f
            val minDim = minOf(cw, ch)

            // ── 1. Starfield ─────────────────────────────────────────────────
            stars.forEach { drawCircle(Color.White.copy(alpha = it.a), it.r, Offset(it.x, it.y)) }

            // ── 2. Orbit paths ───────────────────────────────────────────────
            planets.forEach { p ->
                val a = p.def.orbitFrac * scale
                val b = a * sqrt(1f - p.def.ecc * p.def.ecc)
                drawOval(
                    color   = Color.White.copy(alpha = 0.07f),
                    topLeft = Offset(cx - a, cy - b),
                    size    = Size(a * 2f, b * 2f),
                    style   = Stroke(0.7f),
                )
            }

            // ── 3. Sun (drawn before planets so they orbit over the corona) ──
            val sunC = Offset(cx, cy)
            val sunR = minDim * 0.050f * sunPulse
            drawCircle(Color(0xFFFF7700).copy(alpha = 0.03f), sunR * 11f, sunC)
            drawCircle(Color(0xFFFFAA00).copy(alpha = 0.06f), sunR * 7.5f, sunC)
            drawCircle(Color(0xFFFFCC00).copy(alpha = 0.12f), sunR * 4.5f, sunC)
            drawCircle(Color(0xFFFFDD44).copy(alpha = 0.24f), sunR * 2.6f, sunC)
            drawCircle(Color(0xFFFFEE88),                     sunR,        sunC)
            drawCircle(Color.White.copy(alpha = 0.92f),        sunR * 0.35f, sunC)

            // ── 4. Trails ────────────────────────────────────────────────────
            planets.forEach { p ->
                val r = p.def.bodyRFrac * minDim
                p.trail.forEachIndexed { i, pos ->
                    val frac = 1f - i.toFloat() / p.trail.size
                    drawCircle(p.def.color.copy(alpha = frac * 0.28f), r * frac * 0.70f, pos)
                }
            }

            // ── 5. Per-planet: back ring → body → front ring → moon → label ─
            planets.forEachIndexed { idx, p ->
                val pos = p.pos(cx, cy, scale)
                val r   = p.def.bodyRFrac * minDim

                // Saturn back-half rings appear behind the planet disc
                if (p.def.hasRings) drawSaturnRings(pos, r, front = false)

                drawPlanetBody(p, pos, minDim)

                // Saturn front-half rings appear in front of the planet disc
                if (p.def.hasRings) drawSaturnRings(pos, r, front = true)

                // Earth's moon
                if (p.def.moonOrbitFrac > 0f) {
                    val mp = p.moonPos(cx, cy, scale)
                    val mr = r * 0.38f
                    drawCircle(Color(0xFFCCCCCC).copy(alpha = 0.06f), mr * 4.5f, mp)
                    drawCircle(Color(0xFFCCCCCC), mr, mp)
                    drawCircle(Color.White.copy(alpha = 0.45f), mr * 0.34f,
                        Offset(mp.x - mr * 0.22f, mp.y - mr * 0.22f))
                }

                // Planet name label — positioned above the body
                val layout = labelLayouts[idx]
                drawText(
                    textLayoutResult = layout,
                    color            = p.def.color.copy(alpha = 0.72f),
                    topLeft          = Offset(
                        pos.x - layout.size.width / 2f,
                        pos.y - r - 16f,
                    ),
                )
            }
        }

        // ── HUD ──────────────────────────────────────────────────────────────
        Column(
            modifier              = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 56.dp),
            horizontalAlignment   = Alignment.CenterHorizontally,
        ) {
            Text(
                text          = "SOLAR  SYSTEM",
                color         = Color(0xFFFFCC44),
                fontSize      = 20.sp,
                fontWeight    = FontWeight.Black,
                fontFamily    = FontFamily.Monospace,
                letterSpacing = 5.sp,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text          = "Keplerian orbits  ·  Jetpack Compose",
                color         = Color(0xFF3D4B60),
                fontSize      = 10.sp,
                fontFamily    = FontFamily.Monospace,
                letterSpacing = 1.sp,
            )
        }

    }
}