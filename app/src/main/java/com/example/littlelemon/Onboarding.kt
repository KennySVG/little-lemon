package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.littlelemon.ui.theme.*

@Composable
fun OnboardingScreen(navController: NavHostController) {

    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var registrationMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        TopAppBar(navController = navController)

        // Header bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryGreen)
                .padding(vertical = 24.dp)
        ) {
            Text(
                text = "Let's get to know you",
                fontFamily = Markazi,
                fontSize = 28.sp,
                color = White,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {

            Text(
                text = "Personal information",
                fontFamily = Karla,
                fontSize = 18.sp,
                color = DarkGrey,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            TextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = LightGrey.copy(alpha = 0.3f),
                    focusedContainerColor = LightGrey.copy(alpha = 0.3f)
                )
            )

            TextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = LightGrey.copy(alpha = 0.3f),
                    focusedContainerColor = LightGrey.copy(alpha = 0.3f)
                )
            )

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = LightGrey.copy(alpha = 0.3f),
                    focusedContainerColor = LightGrey.copy(alpha = 0.3f)
                )
            )

            // Registration message
            if (registrationMessage.isNotEmpty()) {
                Text(
                    text = registrationMessage,
                    fontFamily = Karla,
                    fontSize = 14.sp,
                    color = if (registrationMessage.contains("successful")) {
                        PrimaryGreen
                    } else {
                        SecondaryOrange
                    },
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }

            // Register button
            Button(
                onClick = {
                    if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
                        registrationMessage = "Registration unsuccessful. Please enter all data."
                    } else {
                        prefs.saveUser(firstName, lastName, email)
                        registrationMessage = "Registration successful!"
                        navController.navigate(Home.route)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SecondaryYellow
                )
            ) {
                Text(
                    text = "Register",
                    fontFamily = Karla,
                    fontSize = 16.sp,
                    color = DarkGrey
                )
            }
        }
    }
}
