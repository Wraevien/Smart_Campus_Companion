package com.example.smartcampuscompanion.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.smartcampuscompanion.R
import com.example.smartcampuscompanion.data.SessionManager
import com.example.smartcampuscompanion.data.UserRole
import com.example.smartcampuscompanion.navigation.Routes
import com.example.smartcampuscompanion.ui.theme.GreenPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(controller: NavController) {

    val context = LocalContext.current
    val session = remember { SessionManager(context) }
    val colors  = MaterialTheme.colorScheme

    val gradient = Brush.verticalGradient(
        colors = listOf(
            colors.background,
            colors.secondaryContainer,
            colors.background,
        )
    )

    var userName        by remember { mutableStateOf("") }
    var password        by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmVisible  by remember { mutableStateOf(false) }

    var userNameError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmError  by remember { mutableStateOf<String?>(null) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        val (logo, card, footer) = createRefs()

        Image(
            painter            = painterResource(R.drawable.logo),
            contentDescription = "Smart Campus Companion",
            modifier           = Modifier
                .size(320.dp)
                .constrainAs(logo) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .shadow(20.dp, RoundedCornerShape(40.dp))
                .constrainAs(card) {
                    top.linkTo(logo.bottom, margin = (-120).dp)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            shape  = RoundedCornerShape(40.dp),
            colors = CardDefaults.cardColors(containerColor = colors.surface.copy(alpha = 0.97f)),
        ) {
            Column(
                modifier            = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text       = "Create Account",
                    style      = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color      = colors.onSurface,
                )
                Text(
                    text       = "Start your Campus Journey!",
                    style      = MaterialTheme.typography.titleMedium,
                    color      = colors.onSurfaceVariant,
                    fontWeight = FontWeight.Normal,
                    modifier   = Modifier.padding(bottom = 28.dp),
                )

                // ── Username ────────────────────────────────
                OutlinedTextField(
                    value         = userName,
                    onValueChange = { userName = it; userNameError = null },
                    placeholder   = { Text("Enter Username", color = colors.outline) },
                    leadingIcon   = {
                        Icon(Icons.Default.Person, null,
                            tint = if (userNameError != null) colors.error else GreenPrimary)
                    },
                    isError        = userNameError != null,
                    supportingText = userNameError?.let { { Text(it, color = colors.error) } },
                    modifier       = Modifier.fillMaxWidth(),
                    shape          = RoundedCornerShape(16.dp),
                    singleLine     = true,
                    colors         = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor      = GreenPrimary,
                        unfocusedBorderColor    = colors.outline,
                        errorBorderColor        = colors.error,
                        focusedContainerColor   = colors.surfaceVariant,
                        unfocusedContainerColor = colors.surfaceVariant,
                        focusedTextColor        = colors.onSurface,
                        unfocusedTextColor      = colors.onSurface,
                        cursorColor             = GreenPrimary,
                    ),
                )

                Spacer(Modifier.height(12.dp))

                // ── Password ────────────────────────────────
                OutlinedTextField(
                    value         = password,
                    onValueChange = { password = it; passwordError = null },
                    placeholder   = { Text("Enter Password", color = colors.outline) },
                    leadingIcon   = {
                        Icon(Icons.Default.Lock, null,
                            tint = if (passwordError != null) colors.error else GreenPrimary)
                    },
                    trailingIcon  = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector        = if (passwordVisible)
                                    Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                                contentDescription = if (passwordVisible) "Hide" else "Show",
                                tint               = colors.onSurfaceVariant,
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible)
                        VisualTransformation.None else PasswordVisualTransformation(),
                    isError        = passwordError != null,
                    supportingText = passwordError?.let { { Text(it, color = colors.error) } },
                    modifier       = Modifier.fillMaxWidth(),
                    shape          = RoundedCornerShape(16.dp),
                    singleLine     = true,
                    colors         = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor      = GreenPrimary,
                        unfocusedBorderColor    = colors.outline,
                        errorBorderColor        = colors.error,
                        focusedContainerColor   = colors.surfaceVariant,
                        unfocusedContainerColor = colors.surfaceVariant,
                        focusedTextColor        = colors.onSurface,
                        unfocusedTextColor      = colors.onSurface,
                        cursorColor             = GreenPrimary,
                    ),
                )

                Spacer(Modifier.height(12.dp))

                // ── Confirm Password ────────────────────────
                OutlinedTextField(
                    value         = confirmPassword,
                    onValueChange = { confirmPassword = it; confirmError = null },
                    placeholder   = { Text("Confirm Password", color = colors.outline) },
                    leadingIcon   = {
                        Icon(Icons.Default.Lock, null,
                            tint = if (confirmError != null) colors.error else GreenPrimary)
                    },
                    trailingIcon  = {
                        IconButton(onClick = { confirmVisible = !confirmVisible }) {
                            Icon(
                                imageVector        = if (confirmVisible)
                                    Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                                contentDescription = if (confirmVisible) "Hide" else "Show",
                                tint               = colors.onSurfaceVariant,
                            )
                        }
                    },
                    visualTransformation = if (confirmVisible)
                        VisualTransformation.None else PasswordVisualTransformation(),
                    isError        = confirmError != null,
                    supportingText = confirmError?.let { { Text(it, color = colors.error) } },
                    modifier       = Modifier.fillMaxWidth(),
                    shape          = RoundedCornerShape(16.dp),
                    singleLine     = true,
                    colors         = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor      = GreenPrimary,
                        unfocusedBorderColor    = colors.outline,
                        errorBorderColor        = colors.error,
                        focusedContainerColor   = colors.surfaceVariant,
                        unfocusedContainerColor = colors.surfaceVariant,
                        focusedTextColor        = colors.onSurface,
                        unfocusedTextColor      = colors.onSurface,
                        cursorColor             = GreenPrimary,
                    ),
                )

                Spacer(Modifier.height(28.dp))

                // ── Register button ─────────────────────────
                Button(
                    onClick = {
                        var valid = true
                        if (userName.isBlank()) {
                            userNameError = "Username is required"; valid = false
                        }
                        if (password.length < 6) {
                            passwordError = "Password must be at least 6 characters"; valid = false
                        }
                        if (confirmPassword != password) {
                            confirmError = "Passwords do not match"; valid = false
                        }
                        if (valid) {
                            // ✅ Registered users are always STUDENT role
                            session.login(userName, UserRole.STUDENT)
                            controller.navigate(Routes.DASHBOARD) {
                                popUpTo(Routes.LOGIN_REGISTER) { inclusive = true }
                            }
                        }
                    },
                    modifier  = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(6.dp, RoundedCornerShape(18.dp)),
                    shape     = RoundedCornerShape(18.dp),
                    colors    = ButtonDefaults.buttonColors(
                        containerColor = GreenPrimary,
                        contentColor   = colors.onPrimary,
                    ),
                ) {
                    Text(
                        "Register",
                        style      = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Spacer(Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Already have an account? ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.onSurfaceVariant,
                    )
                    TextButton(
                        onClick        = { controller.popBackStack() },
                        contentPadding = PaddingValues(0.dp),
                    ) {
                        Text(
                            "Log in",
                            style      = MaterialTheme.typography.bodyMedium,
                            color      = GreenPrimary,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }

        Text(
            text      = "Developed for Mobile Programming II • 2026",
            style     = MaterialTheme.typography.labelMedium,
            color     = colors.onSurfaceVariant.copy(alpha = 0.6f),
            textAlign = TextAlign.Center,
            modifier  = Modifier.constrainAs(footer) {
                bottom.linkTo(parent.bottom, margin = 20.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
        )
    }
}