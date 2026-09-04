package com.orbit.dashboard.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orbit.R
import kotlinx.coroutines.launch
import kotlin.collections.plusAssign
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

@Composable
fun GravityNebula() {
    var canvasW by remember { mutableFloatStateOf(0f) }
    var canvasH by remember { mutableFloatStateOf(0f) }

    val particles = remember { mutableListOf<Particle>() }
    val stars     = remember { mutableListOf<Star>() }
    var frame     by remember { mutableIntStateOf(0) }

    val wellX    = remember { Animatable(0f) }
    val wellY    = remember { Animatable(0f) }
    var dragging by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    // Central orb breathing pulse
    val pulse by rememberInfiniteTransition(label = "orb").animateFloat(
        initialValue = .80f, targetValue = 1.30f,
        animationSpec = infiniteRepeatable(
            tween(1800, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "pulse"
    )

    // Init once we know canvas dimensions
    LaunchedEffect(canvasW, canvasH) {
        if (canvasW > 0f && particles.isEmpty()) {
            initParticles(particles, canvasW, canvasH)
            stars += makeStars(canvasW, canvasH)
            wellX.snapTo(canvasW / 2f)
            wellY.snapTo(canvasH / 2f)
        }
    }

    // Frame-synced physics loop
    LaunchedEffect(Unit) {
        var prev = 0L
        while (true) {
            withFrameNanos { t ->
                val dt = if (prev == 0L) .016f else ((t - prev) / 1e9f).coerceAtMost(.05f)
                prev = t
                if (particles.isNotEmpty()) {
                    particles.forEach { it.step(wellX.value, wellY.value, dt, canvasW, canvasH) }
                    frame++
                }
            }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color(0xFF06060F))
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged {
                    canvasW = it.width.toFloat()
                    canvasH = it.height.toFloat()
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { pos ->
                            dragging = true
                            scope.launch {
                                wellX.snapTo(pos.x)
                                wellY.snapTo(pos.y)
                            }
                            // Shockwave: push all particles away from touch point
                            particles.forEach { p ->
                                val dx = p.x - pos.x
                                val dy = p.y - pos.y
                                val d  = sqrt(dx * dx + dy * dy).coerceAtLeast(1f)
                                val impulse = (950f / d).coerceAtMost(20f)
                                p.vx += dx / d * impulse
                                p.vy += dy / d * impulse
                            }
                        },
                        onDrag = { change, _ ->
                            change.consume()
                            scope.launch {
                                wellX.snapTo(change.position.x)
                                wellY.snapTo(change.position.y)
                            }
                        },
                        onDragEnd = {
                            dragging = false
                            val cx = canvasW / 2f
                            val cy = canvasH / 2f
                            scope.launch {
                                launch {
                                    wellX.animateTo(
                                        cx,
                                        spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow)
                                    )
                                }
                                launch {
                                    wellY.animateTo(
                                        cy,
                                        spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow)
                                    )
                                }
                            }
                        }
                    )
                }
        ) {
            frame.hashCode()   // read state so this draw block invalidates on every physics tick

            drawStarfield(stars)
            drawFilaments(particles, size.minDimension)
            drawTrails(particles)
            drawParticles(particles)
            drawOrb(wellX.value, wellY.value, pulse, dragging, size.minDimension)
        }

        // ── HUD ──────────────────────────────────────────────────────────────
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .align(Alignment.TopCenter)
//                .padding(top = 60.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text          = "GRAVITY  NEBULA",
//                color         = Color(0xFF00E5FF),
//                fontSize      = 20.sp,
//                fontWeight    = FontWeight.Black,
//                fontFamily    = FontFamily.Monospace,
//                letterSpacing = 5.sp
//            )
//            Spacer(Modifier.height(5.dp))
//            Text(
//                text          = "${particles.size} physics bodies  ·  Jetpack Compose",
//                color         = Color(0xFF3D4E72),
//                fontSize      = 10.sp,
//                fontFamily    = FontFamily.Monospace,
//                letterSpacing = 1.sp
//            )
//        }

        Text(
            text          = "Tap to detonate  ·  Drag to distort  ·  Release to restore",
            modifier      = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 52.dp, start = 24.dp, end = 24.dp),
            color         = colorResource(R.color.dim),
            fontSize      = 11.sp,
            textAlign     = TextAlign.Center
        )
    }
}
// ── Colour ────────────────────────────────────────────────────────────────────

private fun hsl(h: Float, s: Float, l: Float, a: Float = 1f): Color {
    val c = (1f - abs(2f * l - 1f)) * s
    val hp = (h % 360f) / 60f
    val x = c * (1f - abs(hp % 2f - 1f))
    val m = l - c / 2f
    var r = 0f; var g = 0f; var b = 0f
    when (hp.toInt().coerceIn(0, 5)) {
        0 -> { r = c; g = x }
        1 -> { r = x; g = c }
        2 -> { g = c; b = x }
        3 -> { g = x; b = c }
        4 -> { r = x; b = c }
        5 -> { r = c; b = x }
    }
    return Color(r + m, g + m, b + m, a)
}

private fun lerpF(a: Float, b: Float, t: Float) = a + (b - a) * t.coerceIn(0f, 1f)

// ── Particle ──────────────────────────────────────────────────────────────────

private class Particle(
    var x: Float,
    var y: Float,
    var vx: Float = 0f,
    var vy: Float = 0f,
    val radius: Float,
    val baseHue: Float,
    val trailCap: Int = 28
) {
    val trail = ArrayDeque<Offset>(trailCap + 1)
    var speed = 0f

    fun step(wx: Float, wy: Float, dt: Float, w: Float, h: Float) {
        val dx = wx - x
        val dy = wy - y
        val d2 = (dx * dx + dy * dy).coerceAtLeast(2500f)
        val d = sqrt(d2)
        val acc = GRAVITY / d2 * dt
        vx += dx / d * acc
        vy += dy / d * acc
        vx *= DAMP
        vy *= DAMP
        speed = sqrt(vx * vx + vy * vy)
        if (speed > MAX_V) {
            val f = MAX_V / speed; vx *= f; vy *= f; speed = MAX_V
        }
        x += vx
        y += vy
        trail.addFirst(Offset(x, y))
        if (trail.size > trailCap) trail.removeLast()
        // Soft walls
        if (x < 0f)  { x = 0f;  vx =  abs(vx) * .55f }
        if (x > w)   { x = w;   vx = -abs(vx) * .55f  }
        if (y < 0f)  { y = 0f;  vy =  abs(vy) * .55f  }
        if (y > h)   { y = h;   vy = -abs(vy) * .55f  }
    }

    /** Speed fraction 0..1 maps cool base hue → hot orange/white */
    fun colorAt(sf: Float): Color =
        hsl(lerpF(baseHue, 38f, sf), lerpF(.92f, .75f, sf), lerpF(.48f, .78f, sf))

    companion object {
        const val GRAVITY = 3_800_000f
        const val DAMP    = 0.987f
        const val MAX_V   = 22f
    }
}

// ── Background stars ──────────────────────────────────────────────────────────

private class Star(val x: Float, val y: Float, val r: Float, val alpha: Float)

private fun makeStars(w: Float, h: Float) = List(120) {
    Star(
        Random.nextFloat() * w,
        Random.nextFloat() * h,
        Random.nextFloat() * 1.8f + .3f,
        Random.nextFloat() * .45f + .08f
    )
}

// ── Ring initialisation ───────────────────────────────────────────────────────

private fun initParticles(list: MutableList<Particle>, w: Float, h: Float) {
    list.clear()
    val cx = w / 2f
    val cy = h / 2f
    val dim = minOf(w, h)

    fun ring(n: Int, rf: Float, hue: Float, tc: Int, phaseOffset: Float = 0f) {
        val r = dim * rf
        // Orbital velocity from circular-orbit condition: v = sqrt(G*dt / r) * tuning
        val orbV = sqrt(Particle.GRAVITY * 0.016f / r) * .80f
        repeat(n) { i ->
            val a = i * (2 * PI / n).toFloat() + phaseOffset
            val rp = dim * (Random.nextFloat() * .003f + .003f)
            list += Particle(
                x = cx + cos(a) * r,  y = cy + sin(a) * r,
                vx = -sin(a) * orbV,  vy = cos(a) * orbV,
                radius = rp, baseHue = hue, trailCap = tc
            )
        }
    }

    ring(13, .120f, 188f, 22)              // cyan  – inner hot ring
    ring(18, .225f, 268f, 28, .18f)        // violet – first arm
    ring(15, .335f,  46f, 20, .40f)        // gold  – outer arm
    ring( 9, .455f,   8f, 14, .10f)        // ember – far rim
}



// ── DrawScope helpers ─────────────────────────────────────────────────────────

private fun DrawScope.drawStarfield(stars: List<Star>) {
    stars.forEach {
        drawCircle(Color.White.copy(alpha = it.alpha), it.r, Offset(it.x, it.y))
    }
}

private fun DrawScope.drawFilaments(particles: List<Particle>, dim: Float) {
    val threshold = dim * .13f
    val threshSq  = threshold * threshold
    for (i in particles.indices) {
        for (j in i + 1 until particles.size) {
            val a = particles[i]; val b = particles[j]
            val dx = a.x - b.x;  val dy = a.y - b.y
            val d2 = dx * dx + dy * dy
            if (d2 < threshSq) {
                val alpha = (1f - sqrt(d2) / threshold) * .18f
                drawLine(
                    color       = Color.White.copy(alpha = alpha),
                    start       = Offset(a.x, a.y),
                    end         = Offset(b.x, b.y),
                    strokeWidth = 0.7f
                )
            }
        }
    }
}

private fun DrawScope.drawTrails(particles: List<Particle>) {
    particles.forEach { p ->
        val col = p.colorAt(p.speed / Particle.MAX_V)
        p.trail.forEachIndexed { i, pos ->
            val f = 1f - i.toFloat() / p.trail.size
            drawCircle(col.copy(alpha = f * .55f), p.radius * f * .85f, pos)
        }
    }
}

private fun DrawScope.drawParticles(particles: List<Particle>) {
    particles.forEach { p ->
        val sf  = p.speed / Particle.MAX_V
        val col = p.colorAt(sf)
        val c   = Offset(p.x, p.y)
        // Glow halos
        drawCircle(col.copy(alpha = .05f), p.radius * 8f,   c)
        drawCircle(col.copy(alpha = .10f), p.radius * 5f,   c)
        drawCircle(col.copy(alpha = .20f), p.radius * 2.8f, c)
        // Solid core
        drawCircle(col,                    p.radius,         c)
        // Hot-spot specular
        drawCircle(Color.White.copy(alpha = lerpF(.55f, .90f, sf)), p.radius * .38f, c)
    }
}

private fun DrawScope.drawOrb(
    wx: Float, wy: Float,
    pulse: Float,
    dragging: Boolean,
    dim: Float
) {
    val c   = Offset(wx, wy)
    val r   = dim * .026f * pulse
    val col = if (dragging) Color(0xFFFF6B35) else Color(0xFF00E5FF)
    // Layered glow
    drawCircle(col.copy(alpha = .03f), r * 11f,  c)
    drawCircle(col.copy(alpha = .06f), r * 7.5f, c)
    drawCircle(col.copy(alpha = .11f), r * 5f,   c)
    drawCircle(col.copy(alpha = .20f), r * 3f,   c)
    // Solid orb
    drawCircle(col.copy(alpha = .88f), r,         c)
    // Bright centre
    drawCircle(Color.White.copy(alpha = .92f), r * .35f, c)
}