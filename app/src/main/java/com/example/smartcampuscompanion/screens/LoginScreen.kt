package com.example.smartcampuscompanion.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.smartcampuscompanion.R
import com.example.smartcampuscompanion.composables.LoginErrorDialog
import com.example.smartcampuscompanion.composables.ValidationErrorDialog
import com.example.smartcampuscompanion.data.SessionManager
import com.example.smartcampuscompanion.navigation.Routes
import com.example.smartcampuscompanion.ui.theme.BluePrimary
import com.example.smartcampuscompanion.ui.theme.GreenPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(controller: NavController) {

    val context = LocalContext.current
    val session = remember { SessionManager(context) }

    // Elegant blue-to-white gradient
    val gradient = listOf(Color(0xFFE3F2FD), Color(0xFFBBDEFB), Color.White)

    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var showEmptyDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }

    if (showEmptyDialog) {
        ValidationErrorDialog(onDismiss = { showEmptyDialog = false })
    }

    if (showErrorDialog) {
        LoginErrorDialog(onDismiss = { showErrorDialog = false })
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = gradient))
    ) {
        val (logo, card, footer) = createRefs()

        // Logo Section
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Smart Campus Companion",
            modifier = Modifier
                .size(320.dp)
                .constrainAs(logo) {
                    top.linkTo(parent.top, margin = 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        // Main Login Card
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .shadow(20.dp, RoundedCornerShape(40.dp))
                .constrainAs(card) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            shape = RoundedCornerShape(40.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome,",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Glad to see you!",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                OutlinedTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    placeholder = { Text("Username", color = Color.LightGray) },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = BluePrimary) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BluePrimary,
                        unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f),
                        focusedContainerColor = Color(0xFFF5F5F5),
                        unfocusedContainerColor = Color(0xFFF5F5F5)
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Password", color = Color.LightGray) },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = BluePrimary) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BluePrimary,
                        unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f),
                        focusedContainerColor = Color(0xFFF5F5F5),
                        unfocusedContainerColor = Color(0xFFF5F5F5)
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (userName.isNotEmpty() && password.isNotEmpty()) {
                            if (userName == "admin" && password == "admin") {
                                session.login(userName)
                                controller.navigate(Routes.DASHBOARD) {
                                    popUpTo(Routes.LOGIN) { inclusive = true }
                                }
                            } else {
                                showErrorDialog = true
                            }
                        } else {
                            showEmptyDialog = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .shadow(8.dp, RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
                ) {
                    Text(
                        "Login",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Replaced Sign Up button with a text link
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Don't have an account? ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    TextButton(
                        onClick = { controller.navigate("register") },
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            "Sign up",
                            style = MaterialTheme.typography.bodyMedium,
                            color = BluePrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Text(
            text = "Developed for Mobile Programming II • 2026",
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(footer) {
                bottom.linkTo(parent.bottom, margin = 24.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}
