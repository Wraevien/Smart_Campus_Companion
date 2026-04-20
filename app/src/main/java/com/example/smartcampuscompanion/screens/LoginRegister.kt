package com.example.smartcampuscompanion.screens

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.smartcampuscompanion.R
import com.example.smartcampuscompanion.navigation.Routes

@Composable
fun LoginRegister(controller: NavController) {
    val colors = MaterialTheme.colorScheme

    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            colors.background,
            colors.primaryContainer,
            colors.background,
        )
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color    = Color.Transparent,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundGradient)
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 28.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Spacer(Modifier.height(8.dp))

            // Logo
            Image(
                painter            = painterResource(id = R.drawable.logotwo),
                contentDescription = "Smart Campus Companion",
                modifier           = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale       = ContentScale.Fit,
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text       = "Smart Campus Companion",
                fontSize   = 26.sp,
                fontWeight = FontWeight.Bold,
                color      = colors.primary,
                textAlign  = TextAlign.Center,
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text      = "\"Navigating Campus Life Together.\"",
                fontStyle = FontStyle.Italic,
                fontSize  = 14.sp,
                color     = colors.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text       = "Stay updated with campus announcements, schedules, and student tools in one smart companion app.",
                fontSize   = 14.sp,
                lineHeight = 20.sp,
                color      = colors.onSurface.copy(alpha = 0.72f),
                textAlign  = TextAlign.Center,
                modifier   = Modifier.fillMaxWidth(0.88f),
            )

            Spacer(Modifier.height(28.dp))

            HorizontalDivider(
                modifier  = Modifier.fillMaxWidth(0.3f),
                thickness = 1.dp,
                color     = colors.outline.copy(alpha = 0.4f),
            )

            Spacer(Modifier.height(32.dp))

            // Login button
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .shadow(elevation = 6.dp, shape = RoundedCornerShape(18.dp), clip = false),
                onClick  = { controller.navigate(Routes.LOGIN) },
                colors   = ButtonDefaults.buttonColors(
                    containerColor = colors.primary,
                    contentColor   = colors.onPrimary,
                ),
                shape     = RoundedCornerShape(18.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
            ) {
                Text(
                    text       = "Login",
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(Modifier.height(14.dp))

            // Register button
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .shadow(elevation = 6.dp, shape = RoundedCornerShape(18.dp), clip = false),
                onClick  = { controller.navigate(Routes.REGISTER) },
                colors   = ButtonDefaults.buttonColors(
                    containerColor = colors.secondary,
                    contentColor   = colors.onSecondary,
                ),
                shape     = RoundedCornerShape(18.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
            ) {
                Text(
                    text       = "Get Started",
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(Modifier.height(40.dp))

            Text(
                text      = "Developed for Mobile Programming II • 2026",
                color     = colors.onSurfaceVariant.copy(alpha = 0.6f),
                textAlign = TextAlign.Center,
                fontSize  = 12.sp,
            )

            Spacer(Modifier.height(8.dp))
        }
    }
}