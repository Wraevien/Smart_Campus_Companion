package com.example.smartcampuscompanion.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.smartcampuscompanion.R
import com.example.smartcampuscompanion.navigation.Routes

@Composable
fun LoginRegister(controller: NavController) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val (col, footerText) = createRefs()

        Text(
            text = "Developed for Mobile Programming II • 2026",
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .constrainAs(footerText) {
                    bottom.linkTo(parent.bottom)
                }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(col) {
                    top.linkTo(parent.top)
                    bottom.linkTo(footerText.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Smart Campus Companion",
                modifier = Modifier.size(180.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Smart Campus Companion",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF015DB6)
            )

            Text(
                text = "\"Navigating Campus Life Together.\"",
                fontStyle = FontStyle.Italic,
                fontSize = 15.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                modifier = Modifier
                    .width(300.dp)
                    .height(56.dp),
                onClick = {
                    controller.navigate(Routes.LOGIN)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF015DB6),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
            ) {
                Text("Login", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier
                    .width(300.dp)
                    .height(56.dp),
                onClick = {
                    controller.navigate(Routes.REGISTER)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF599E29),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
            ) {
                Text("Get Started", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
