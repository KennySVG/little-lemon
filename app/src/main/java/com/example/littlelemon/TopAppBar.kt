package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun TopAppBar(navController: NavHostController? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp), // explicit bar height
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(modifier = Modifier.width(6.dp))

        // Left spacer (balances profile icon)
        Spacer(modifier = Modifier.size(40.dp))

        // Centered logo
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Little Lemon Logo",
                modifier = Modifier
                    .fillMaxHeight(0.7f), // scales UP properly
                contentScale = ContentScale.FillHeight
            )
        }

        // Profile icon
        Image(
            painter = painterResource(R.drawable.profile),
            contentDescription = "Profile",
            modifier = Modifier
                .size(40.dp)
                .padding(end = 6.dp)
                .clickable {
                    navController?.navigate(Profile.route)
                },
            contentScale = ContentScale.Fit
        )
    }
}


@Preview(showBackground = true)
@Composable
fun TopAppBarPreview() {
    LittleLemonTheme {
        TopAppBar()
    }
}
