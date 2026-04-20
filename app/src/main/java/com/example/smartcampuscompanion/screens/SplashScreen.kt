package com.example.smartcampuscompanion.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.smartcampuscompanion.data.SessionManager
import com.example.smartcampuscompanion.navigation.Routes
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(controller: NavController) {
    val context = LocalContext.current
    val session = remember { SessionManager(context) }

    // ── Animation states ──────────────────────────────────────────────
    val logoScale     = remember { Animatable(0.4f) }
    val contentAlpha  = remember { Animatable(0f) }
    val taglineOffset = remember { Animatable(30f) }
    val dotsAlpha     = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // Phase 1: logo pops in + content fades in (parallel)
        coroutineScope {
            launch {
                logoScale.animateTo(
                    targetValue   = 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness    = Spring.StiffnessMedium,
                    )
                )
            }
            launch {
                contentAlpha.animateTo(1f, animationSpec = tween(500))
            }
        }

        delay(300)

        // Phase 2: tagline slides up
        taglineOffset.animateTo(0f, animationSpec = tween(400, easing = FastOutSlowInEasing))

        delay(200)

        // Phase 3: loading dots appear
        dotsAlpha.animateTo(1f, animationSpec = tween(300))

        // Phase 4: hold briefly then navigate
        delay(900)

        val dest = if (session.isLoggedIn()) Routes.DASHBOARD else Routes.LOGIN_REGISTER
        controller.navigate(dest) {
            popUpTo(Routes.SPLASH) { inclusive = true }
        }
    }

    // ── Pulsing dots ──────────────────────────────────────────────────
    val infiniteTransition = rememberInfiniteTransition(label = "dots")
    val dot1Alpha by infiniteTransition.animateFloat(
        initialValue  = 0.3f, targetValue  = 1f,
        animationSpec = infiniteRepeatable(tween(600), RepeatMode.Reverse, initialStartOffset = StartOffset(0)),
        label = "d1",
    )
    val dot2Alpha by infiniteTransition.animateFloat(
        initialValue  = 0.3f, targetValue  = 1f,
        animationSpec = infiniteRepeatable(tween(600), RepeatMode.Reverse, initialStartOffset = StartOffset(200)),
        label = "d2",
    )
    val dot3Alpha by infiniteTransition.animateFloat(
        initialValue  = 0.3f, targetValue  = 1f,
        animationSpec = infiniteRepeatable(tween(600), RepeatMode.Reverse, initialStartOffset = StartOffset(400)),
        label = "d3",
    )

    // ── UI ────────────────────────────────────────────────────────────
    val gradient = Brush.verticalGradient(
        listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.primary.copy(alpha = 0.85f),
            MaterialTheme.colorScheme.background,
        )
    )

    Box(
        modifier         = Modifier.fillMaxSize().background(gradient),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier            = Modifier.alpha(contentAlpha.value),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Logo tile
            Surface(
                modifier        = Modifier
                    .size(120.dp)
                    .scale(logoScale.value),
                shape           = RoundedCornerShape(32.dp),
                color           = Color.White,
                shadowElevation = 20.dp,
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector        = Icons.Outlined.School,
                        contentDescription = "Smart Campus",
                        tint               = MaterialTheme.colorScheme.primary,
                        modifier           = Modifier.size(64.dp),
                    )
                }
            }

            Spacer(Modifier.height(28.dp))

            // App name
            Text(
                text       = "Smart Campus",
                style      = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color      = Color.White,
                textAlign  = TextAlign.Center,
            )
            Text(
                text       = "Companion",
                style      = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color      = Color.White.copy(alpha = 0.85f),
                textAlign  = TextAlign.Center,
            )

            Spacer(Modifier.height(8.dp))

            // Tagline
            Text(
                text      = "Your campus, smarter.",
                style     = MaterialTheme.typography.bodyMedium,
                color     = Color.White.copy(alpha = 0.70f),
                textAlign = TextAlign.Center,
                modifier  = Modifier.offset(y = taglineOffset.value.dp),
            )

            Spacer(Modifier.height(60.dp))

            // Loading dots
            Row(
                modifier              = Modifier.alpha(dotsAlpha.value),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment     = Alignment.CenterVertically,
            ) {
                listOf(dot1Alpha, dot2Alpha, dot3Alpha).forEach { a ->
                    Surface(
                        modifier = Modifier.size(10.dp).alpha(a),
                        shape    = CircleShape,
                        color    = Color.White,
                    ) {}
                }
            }
        }

        // Version tag
        Text(
            text     = "v1.0",
            style    = MaterialTheme.typography.labelSmall,
            color    = Color.White.copy(alpha = 0.4f),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
        )
    }
}