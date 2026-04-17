package com.example.smartcampuscompanion.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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

private val LandingBlue = Color(0xFF015DB6)
private val LandingGreen = Color(0xFF599E29)
private val LandingTextPrimary = Color(0xFF1E1E1E)
private val LandingTextSecondary = Color(0xFF6E6E6E)

@Composable
fun LoginRegister(controller: NavController) {
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFDCECF8),
            Color(0xFFEAF5FC),
            Color(0xFFF8FCFF)
        )
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundGradient)
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(id = R.drawable.logotwo),
                contentDescription = "Smart Campus Companion",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Smart Campus Companion",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = LandingBlue,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "\"Navigating Campus Life Together.\"",
                fontStyle = FontStyle.Italic,
                fontSize = 15.sp,
                color = LandingTextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Stay updated with campus announcements, schedules, and student tools in one smart companion app.",
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = LandingTextPrimary.copy(alpha = 0.78f),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(0.9f)
            )

            Spacer(modifier = Modifier.height(22.dp))

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(0.34f),
                thickness = 1.dp,
                color = Color(0x33000000)
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(18.dp),
                        clip = false
                    ),
                onClick = {
                    controller.navigate(Routes.LOGIN)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = LandingBlue,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(18.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Text(
                    text = "Login",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(18.dp),
                        clip = false
                    ),
                onClick = {
                    controller.navigate(Routes.REGISTER)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = LandingGreen,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(18.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Text(
                    text = "Get Started",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(42.dp))

            Text(
                text = "Developed for Mobile Programming II • 2026",
                color = LandingTextSecondary,
                textAlign = TextAlign.Center,
                fontSize = 12.sp
            )
        }
    }
}