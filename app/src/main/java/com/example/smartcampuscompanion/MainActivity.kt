package com.example.smartcampuscompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartcampuscompanion.data.SessionManager
import com.example.smartcampuscompanion.data.db.DatabaseProvider
import com.example.smartcampuscompanion.data.repository.TaskRepository
import com.example.smartcampuscompanion.navigation.Routes
import com.example.smartcampuscompanion.screens.*
import com.example.smartcampuscompanion.ui.announcements.AnnouncementsScreen
import com.example.smartcampuscompanion.ui.announcements.PostAnnouncementScreen
import com.example.smartcampuscompanion.ui.dashboard.DashboardScreen
import com.example.smartcampuscompanion.ui.login.LoginScreen
import com.example.smartcampuscompanion.ui.theme.SmartCampusCompanionTheme
import com.example.smartcampuscompanion.viewmodel.TaskViewModel
import com.example.smartcampuscompanion.viewmodel.TaskViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val session = SessionManager(this)

        setContent {
            // Seed the theme with the saved preference so it persists across launches
            SmartCampusCompanionTheme(initialDark = session.isDarkMode()) {
                Screens()
            }
        }
    }
}

@Composable
fun Screens() {
    val controller = rememberNavController()
    val context    = LocalContext.current
    val session    = remember { SessionManager(context) }

    val database      = remember { DatabaseProvider.get(context) }
    val repository    = remember { TaskRepository(database.taskDao()) }
    val taskViewModel: TaskViewModel = viewModel(factory = TaskViewModelFactory(repository))

    val startDestination = if (session.isLoggedIn()) Routes.SPLASH else Routes.SPLASH

    NavHost(controller, startDestination = startDestination) {
        composable(Routes.SPLASH)            { SplashScreen(controller) }
        composable(Routes.LOGIN_REGISTER)    { LoginRegister(controller) }
        composable(Routes.LOGIN)             { LoginScreen(controller) }
        composable(Routes.REGISTER)          { RegisterScreen(controller) }
        composable(Routes.DASHBOARD)         { DashboardScreen(controller, taskViewModel) }
        composable(Routes.CAMPUS_INFO)       { CampusInfoScreen(controller) }
        composable(Routes.TASK_MANAGER)      { TaskManagerScreen(navController = controller, viewModel = taskViewModel) }
        composable(Routes.ANNOUNCEMENTS)     { AnnouncementsScreen(controller) }
        composable(Routes.POST_ANNOUNCEMENT) { PostAnnouncementScreen(controller) }
        composable(Routes.PROFILE)           { ProfileScreen(controller) }
    }
}