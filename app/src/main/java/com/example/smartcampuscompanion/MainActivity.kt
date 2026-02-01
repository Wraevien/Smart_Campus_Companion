package com.example.smartcampuscompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartcampuscompanion.screens.LoginRegister
import com.example.smartcampuscompanion.screens.LoginScreen
import com.example.smartcampuscompanion.screens.RegisterScreen
import com.example.smartcampuscompanion.ui.theme.SmartCampusCompanionTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.smartcampuscompanion.data.SessionManager
import com.example.smartcampuscompanion.navigation.Routes


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                Screens();
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

        // placeholder muna (member assigned sa dashboard will replace)
        composable(Routes.DASHBOARD) {
            Text("Dashboard placeholder")
        }

        // placeholder muna (member assigned sa campus will replace)
        composable(Routes.CAMPUS_INFO) {
            Text("Campus Info placeholder")
        }
    }
}
