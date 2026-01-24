package com.example.smartcampuscompanion.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.smartcampuscompanion.R
@Composable
fun LoginRegister(controller: NavController){

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){

        val (col, footerText) = createRefs();

        Text("Developed for Mobile Programming II • 2026", color = Color.DarkGray, textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(footerText){
                    bottom.linkTo(parent.bottom)
                });

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(550.dp)
                .background(Color.White)
                .constrainAs(col){
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Smart Campus Companion",
                modifier = Modifier
                    .offset(y = -60.dp)
            )

            Text("\"Navigating Campus Life Together.\"", fontStyle = FontStyle.Italic , fontSize = 15.sp, modifier = Modifier.offset(y = -160.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = -80.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Button(
                    modifier = Modifier
                        .width(300.dp),
                        onClick = {
                            controller.navigate("login")
                        },
                        colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF015DB6),
                        contentColor = Color.White
                    ), elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 6.dp
                    )
                ) {
                    Text("Login");
                }

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF599E29),
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 6.dp
                    ),
                    modifier = Modifier
                        .width(300.dp),
                    onClick = {
                        controller.navigate("register")
                    }) {
                    Text("Sign Up");
                }

            }
        }
    }

}