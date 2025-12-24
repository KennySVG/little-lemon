package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.littlelemon.ui.theme.*

@Composable
fun UpperPanel(searchPhrase: String, onSearchChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .background(PrimaryGreen)
            .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp)
    ) {
        Text(
            text = "Little Lemon",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = SecondaryYellow,
            fontFamily = Markazi
        )
        Text(
            text = "Chicago",
            fontSize = 24.sp,
            color = White,
            fontFamily = Markazi
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(top = 20.dp)
        ) {
            Text(
                text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(bottom = 28.dp, end = 20.dp)
                    .fillMaxWidth(0.6f),
                color = White,
                fontFamily = Karla
            )
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Hero Image",
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
        }

        // Search TextField placed directly below the hero text/image
        TextField(
            value = searchPhrase,
            onValueChange = onSearchChange,
            placeholder = { Text("Enter Search Phrase") },
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            singleLine = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UpperPanelPreview() {
    LittleLemonTheme {
        UpperPanel(searchPhrase = "", onSearchChange = {})
    }
}
