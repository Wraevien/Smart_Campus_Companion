package com.example.smartcampuscompanion.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.smartcampuscompanion.R

@Composable
fun RegisterScreen(controller: NavController){

    val gradient = listOf(Color(0xFFFFFFFF), Color(0xFFb2e28f))

    //fields
    var userName by remember{mutableStateOf("")};
    var password by remember{mutableStateOf("")};

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = gradient))
    ){

        val (card, logo) = createRefs();

        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Smart Campus Companion",
            modifier = Modifier
                .offset(y = 130.dp)
                .constrainAs(logo){
                    bottom.linkTo(card.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        ElevatedCard(
            modifier = Modifier
                .constrainAs(card){
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(500.dp)
                .width(360.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ){

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ){

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text("Create Account", fontSize = 40.sp, fontWeight = FontWeight.W400, color = Color.Black, fontFamily = FontFamily.Serif)
                    Text("Start your Campus Journey!", fontSize = 20.sp, color = Color.Black, fontFamily = FontFamily.Serif)
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){

                    TextField(
                        value = userName,
                        onValueChange = { userName = it },
                        placeholder = { Text("Username") },
                        label = { Text("Enter Username") },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            unfocusedLabelColor = Color.Black,
                            focusedPlaceholderColor = Color.Black
                        ),
                        modifier = Modifier
                            .border(1.dp, Color(0xFF015ec1))
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Enter Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            unfocusedLabelColor = Color.Black,
                        ),
                        modifier = Modifier
                            .border(1.dp, Color(0xFF015ec1))
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 50.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

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
                                controller.popBackStack()
                            }) {
                            Text("Register");
                        }

                        Button(
                            modifier = Modifier
                                .width(300.dp),
                            onClick = {
                                controller.popBackStack()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF015DB6),
                                contentColor = Color.White
                            ),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 6.dp
                            )
                        ) {
                            Text("Back");
                        }

                    }
                }
            }
        }
    }
}