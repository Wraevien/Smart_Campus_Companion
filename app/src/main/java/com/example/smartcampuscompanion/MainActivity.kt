package com.example.smartcampuscompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartcampuscompanion.data.SessionManager
import com.example.smartcampuscompanion.navigation.Routes
import com.example.smartcampuscompanion.screens.CampusInfoScreen
import com.example.smartcampuscompanion.screens.DashboardScreen
import com.example.smartcampuscompanion.screens.LoginRegister
import com.example.smartcampuscompanion.screens.LoginScreen
import com.example.smartcampuscompanion.screens.RegisterScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Screens()
        }
    }
}

@Composable
fun Screens() {
    val controller = rememberNavController()
    val context = LocalContext.current
    val session = remember { SessionManager(context) }

    val startDestination = if (session.isLoggedIn()) Routes.DASHBOARD else Routes.LOGIN_REGISTER

    NavHost(controller, startDestination) {

        composable(Routes.LOGIN_REGISTER) { LoginRegister(controller) }
        composable(Routes.LOGIN) { LoginScreen(controller) }
        composable(Routes.REGISTER) { RegisterScreen(controller) }
        composable(Routes.DASHBOARD) { DashboardScreen(controller) }
        composable(Routes.CAMPUS_INFO) { CampusInfoScreen(controller) }
    }
}