package com.example.littlelemon

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun DishDetails(navController: NavHostController, id: Int, database: AppDatabase) {
    var menuItemRoom by remember { mutableStateOf<MenuItemRoom?>(null) }
    
    // Load dish from database
    LaunchedEffect(id) {
        withContext(Dispatchers.IO) {
            menuItemRoom = database.menuDao().getMenuItemById(id)
        }
    }
    
    val dish = menuItemRoom?.let { Dish.fromMenuItemRoom(it) }
    
    if (dish == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading...", fontFamily = Karla)
        }
        return
    }
    
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopAppBar(navController = navController)
        
        AsyncImage(
            model = dish.image,
            contentDescription = "Dish image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )
        
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = dish.name,
                fontFamily = Markazi,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGrey
            )
            Text(
                text = dish.description,
                fontFamily = Karla,
                fontSize = 16.sp,
                color = DarkGrey
            )
        }
        
        Counter()
        
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = SecondaryYellow
            )
        ) {
            Text(
                text = "Add for $${String.format("%.2f", dish.price)}",
                fontFamily = Karla,
                fontSize = 16.sp,
                color = DarkGrey,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun Counter() {
    var counter by remember { mutableStateOf(1) }
    
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        TextButton(
            onClick = {
                if (counter > 1) counter--
            }
        ) {
            Text(
                text = "-",
                fontFamily = Markazi,
                fontSize = 24.sp,
                color = DarkGrey
            )
        }
        Text(
            text = counter.toString(),
            fontFamily = Markazi,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = DarkGrey,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        TextButton(
            onClick = {
                counter++
            }
        ) {
            Text(
                text = "+",
                fontFamily = Markazi,
                fontSize = 24.sp,
                color = DarkGrey
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DishDetailsPreview() {
    LittleLemonTheme {
        // Preview without navController and database
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Dish Details Preview")
        }
    }
}
