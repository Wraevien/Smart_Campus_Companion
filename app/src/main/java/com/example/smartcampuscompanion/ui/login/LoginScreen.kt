package com.example.smartcampuscompanion.ui.login

import androidx.compose.animation.*
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
import com.example.smartcampuscompanion.composables.LoginErrorDialog
import com.example.smartcampuscompanion.composables.ValidationErrorDialog
import com.example.smartcampuscompanion.data.SessionManager
import com.example.smartcampuscompanion.data.UserRole
import com.example.smartcampuscompanion.navigation.Routes
import com.example.smartcampuscompanion.ui.theme.BluePrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(controller: NavController) {

    val context = LocalContext.current
    val session = remember { SessionManager(context) }
    val colors  = MaterialTheme.colorScheme

    val gradient = Brush.verticalGradient(
        colors = listOf(
            colors.background,
            colors.primaryContainer,
            colors.background,
        )
    )

    var userName        by remember { mutableStateOf("") }
    var password        by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading       by remember { mutableStateOf(false) }

    var userNameError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    var selectedRole by remember { mutableStateOf(UserRole.STUDENT) }

    var showEmptyDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage    by remember { mutableStateOf<String?>(null) }

    if (showEmptyDialog) {
        ValidationErrorDialog(onDismiss = { showEmptyDialog = false })
    }
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false; errorMessage = null },
            title = { Text("Login Failed") },
            text = { Text(errorMessage ?: "Please check your credentials and try again.") },
            confirmButton = {
                Button(onClick = { showErrorDialog = false; errorMessage = null }) {
                    Text("OK")
                }
            }
        )
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        val (logo, card, footer) = createRefs()

        Image(
            painter            = painterResource(R.drawable.logo),
            contentDescription = "Smart Campus Companion",
            modifier           = Modifier
                .size(340.dp)
                .constrainAs(logo) {
                    top.linkTo(parent.top, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .shadow(20.dp, RoundedCornerShape(40.dp))
                .constrainAs(card) {
                    top.linkTo(logo.bottom, margin = (-140).dp)
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
                    text       = "Welcome,",
                    style      = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color      = colors.onSurface,
                )
                Text(
                    text     = "Glad to see you!",
                    style    = MaterialTheme.typography.titleMedium,
                    color    = colors.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 28.dp),
                )

                // ── Username field ──────────────────────────
                OutlinedTextField(
                    value         = userName,
                    onValueChange = { userName = it; userNameError = null },
                    placeholder   = { Text("Username", color = colors.outline) },
                    leadingIcon   = {
                        Icon(Icons.Default.Person, null,
                            tint = if (userNameError != null) colors.error else BluePrimary)
                    },
                    isError        = userNameError != null,
                    supportingText = userNameError?.let { { Text(it, color = colors.error) } },
                    modifier       = Modifier.fillMaxWidth(),
                    shape          = RoundedCornerShape(16.dp),
                    singleLine     = true,
                    enabled        = !isLoading,
                    colors         = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor      = BluePrimary,
                        unfocusedBorderColor    = colors.outline,
                        errorBorderColor        = colors.error,
                        focusedContainerColor   = colors.surfaceVariant,
                        unfocusedContainerColor = colors.surfaceVariant,
                        focusedTextColor        = colors.onSurface,
                        unfocusedTextColor      = colors.onSurface,
                        cursorColor             = BluePrimary,
                    ),
                )

                Spacer(Modifier.height(12.dp))

                // ── Password field ──────────────────────────
                OutlinedTextField(
                    value         = password,
                    onValueChange = { password = it; passwordError = null },
                    placeholder   = { Text("Password", color = colors.outline) },
                    leadingIcon   = {
                        Icon(Icons.Default.Lock, null,
                            tint = if (passwordError != null) colors.error else BluePrimary)
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
                    enabled        = !isLoading,
                    colors         = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor      = BluePrimary,
                        unfocusedBorderColor    = colors.outline,
                        errorBorderColor        = colors.error,
                        focusedContainerColor   = colors.surfaceVariant,
                        unfocusedContainerColor = colors.surfaceVariant,
                        focusedTextColor        = colors.onSurface,
                        unfocusedTextColor      = colors.onSurface,
                        cursorColor             = BluePrimary,
                    ),
                )

                Spacer(Modifier.height(12.dp))

                // ── Role Selection ──────────────────────────
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selectedRole == UserRole.STUDENT,
                            onClick = { selectedRole = UserRole.STUDENT },
                            colors = RadioButtonDefaults.colors(selectedColor = BluePrimary)
                        )
                        Text("Student", color = colors.onSurface)
                    }
                    Spacer(Modifier.width(20.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selectedRole == UserRole.ADMIN,
                            onClick = { selectedRole = UserRole.ADMIN },
                            colors = RadioButtonDefaults.colors(selectedColor = BluePrimary)
                        )
                        Text("Admin", color = colors.onSurface)
                    }
                }

                Spacer(Modifier.height(20.dp))

                // ── Login button ────────────────────────────
                Button(
                    onClick = {
                        var valid = true
                        if (userName.isBlank()) {
                            userNameError = "Username is required"
                            valid = false
                        }
                        if (password.isBlank()) {
                            passwordError = "Password is required"
                            valid = false
                        }
                        if (!valid) return@Button

                        isLoading = true
                        
                        // Firebase Auth Login
                        val auth = com.google.firebase.auth.FirebaseAuth.getInstance()
                        val email = if (userName.contains("@")) userName else "$userName@campus.com"
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val uid = auth.currentUser?.uid ?: ""
                                    // Fetch role from Firestore
                                    com.google.firebase.firestore.FirebaseFirestore.getInstance()
                                        .collection("users").document(uid).get()
                                        .addOnSuccessListener { doc ->
                                            val roleStr = doc.getString("role") ?: "STUDENT"
                                            val dbRole = if (roleStr == "ADMIN") UserRole.ADMIN else UserRole.STUDENT
                                            
                                            // Check if selected role matches database role
                                            if (dbRole == selectedRole) {
                                                session.login(userName, dbRole, uid)
                                                controller.navigate(Routes.DASHBOARD) {
                                                    popUpTo(Routes.LOGIN) { inclusive = true }
                                                }
                                            } else {
                                                isLoading = false
                                                errorMessage = "Role mismatch: You are logged in as ${dbRole.name}"
                                                showErrorDialog = true
                                            }
                                        }
                                        .addOnFailureListener {
                                            isLoading = false
                                            showErrorDialog = true
                                        }
                                } else {
                                    isLoading = false
                                    showErrorDialog = true
                                }
                            }
                    },
                    modifier  = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(6.dp, RoundedCornerShape(18.dp)),
                    shape     = RoundedCornerShape(18.dp),
                    enabled   = !isLoading,
                    colors    = ButtonDefaults.buttonColors(
                        containerColor = BluePrimary,
                        contentColor   = colors.onPrimary,
                    ),
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier    = Modifier.size(24.dp),
                            color       = colors.onPrimary,
                            strokeWidth = 2.dp,
                        )
                    } else {
                        Text(
                            "Login",
                            style      = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Don't have an account? ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.onSurfaceVariant,
                    )
                    TextButton(
                        onClick        = { controller.navigate("register") },
                        contentPadding = PaddingValues(0.dp),
                        enabled        = !isLoading,
                    ) {
                        Text(
                            "Sign up",
                            style      = MaterialTheme.typography.bodyMedium,
                            color      = BluePrimary,
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
