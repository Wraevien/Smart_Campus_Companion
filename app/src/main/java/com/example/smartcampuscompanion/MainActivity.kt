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
import com.example.smartcampuscompanion.screens.AnnouncementsScreen
import com.example.smartcampuscompanion.screens.CampusInfoScreen
import com.example.smartcampuscompanion.screens.DashboardScreen
import com.example.smartcampuscompanion.screens.LoginRegister
import com.example.smartcampuscompanion.screens.LoginScreen
import com.example.smartcampuscompanion.screens.RegisterScreen
import com.example.smartcampuscompanion.screens.TaskManagerScreen
import com.example.smartcampuscompanion.ui.theme.SmartCampusCompanionTheme
import com.example.smartcampuscompanion.viewmodel.TaskViewModel
import com.example.smartcampuscompanion.viewmodel.TaskViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartCampusCompanionTheme(dynamicColor = false) {
                Screens()
            }
        }
    }
}

@Composable
fun Screens() {
    val controller = rememberNavController()
    val context = LocalContext.current
    val session = remember { SessionManager(context) }

    // Initialize Database, Repository, and ViewModel
    val database = remember { DatabaseProvider.get(context) }
    val repository = remember { TaskRepository(database.taskDao()) }
    val taskViewModel: TaskViewModel = viewModel(
        factory = TaskViewModelFactory(repository)
    )

    val startDestination = if (session.isLoggedIn()) Routes.DASHBOARD else Routes.LOGIN_REGISTER

    NavHost(controller, startDestination) {
        composable(Routes.LOGIN_REGISTER) { LoginRegister(controller) }
        composable(Routes.LOGIN) { LoginScreen(controller) }
        composable(Routes.REGISTER) { RegisterScreen(controller) }
        composable(Routes.DASHBOARD) { DashboardScreen(controller, taskViewModel) }
        composable(Routes.CAMPUS_INFO) { CampusInfoScreen(controller) }
<<<<<<< HEAD
        composable(Routes.TASK_MANAGER) { 
            TaskManagerScreen(navController = controller, viewModel = taskViewModel) 
        }
=======
        composable(Routes.ANNOUNCEMENTS) { AnnouncementsScreen(controller) }
>>>>>>> c761885fa3313dfbab815bf276d0533db1f8710c
    }
}
