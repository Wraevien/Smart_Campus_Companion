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
fun Screens(){

    val controller = rememberNavController();

    val navHost = NavHost(controller, "login/register"){

        composable("login/register"){
            LoginRegister(controller);
        }

        composable("login"){
            LoginScreen(controller);
        }

        composable("register"){
            RegisterScreen(controller);
        }

    }

}

