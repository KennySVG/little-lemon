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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.littlelemon.ui.theme.*

@Composable
fun Profile(navController: NavHostController) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }

    var firstName by remember { mutableStateOf(prefs.getFirstName() ?: "") }
    var lastName by remember { mutableStateOf(prefs.getLastName() ?: "") }
    var email by remember { mutableStateOf(prefs.getEmail() ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        TopAppBar(navController = navController)

        // Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Text(
                text = "Profile information:",
                fontFamily = Karla,
                fontSize = 18.sp,
                color = DarkGrey,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // First Name
            OutlinedTextField(
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

            // Last Name
            OutlinedTextField(
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

            // Email
            OutlinedTextField(
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

            // Save button
            Button(
                onClick = {
                    prefs.saveUser(firstName, lastName, email)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryGreen
                )
            ) {
                Text(
                    text = "Save",
                    fontFamily = Karla,
                    fontSize = 16.sp,
                    color = White
                )
            }

            // Log out button
            Button(
                onClick = {
                    prefs.clearUserData()
                    navController.navigate(Onboarding.route) {
                        popUpTo(Home.route) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SecondaryYellow
                )
            ) {
                Text(
                    text = "Log out",
                    fontFamily = Karla,
                    fontSize = 16.sp,
                    color = DarkGrey
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    LittleLemonTheme {
        // Preview without navController
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Profile Screen Preview")
        }
    }
}
