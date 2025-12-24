package com.example.littlelemon

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.ui.theme.White
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun Home(navController: NavHostController, database: AppDatabase) {
    var menuItemsRoom by remember { mutableStateOf<List<MenuItemRoom>>(emptyList()) }
    // Search phrase state used by UpperPanel TextField
    var searchPhrase by remember { mutableStateOf("") }
    
    // Load data from database - check periodically until data is loaded
    LaunchedEffect(Unit) {
        while (menuItemsRoom.isEmpty()) {
            withContext(Dispatchers.IO) {
                val items = database.menuDao().getAll()
                menuItemsRoom = items
                Log.d("Home", "Loaded ${items.size} items from database")
            }
            if (menuItemsRoom.isEmpty()) {
                delay(1000) // Wait 1 second before checking again
            }
        }
        
        // Continue checking periodically for updates (every 2 seconds)
        while (true) {
            delay(2000)
            withContext(Dispatchers.IO) {
                val newItems = database.menuDao().getAll()
                if (newItems != menuItemsRoom) {
                    menuItemsRoom = newItems
                    Log.d("Home", "Updated to ${newItems.size} items")
                }
            }
        }
    }
    
    // Convert Room entities to Dish objects
    val allDishes = menuItemsRoom.map { Dish.fromMenuItemRoom(it) }

    // Filter dishes by search phrase (only if search is not blank)
    val dishes = remember(allDishes, searchPhrase) {
        if (searchPhrase.isBlank()) {
            allDishes
        } else {
            allDishes.filter { dish ->
                dish.name.contains(searchPhrase, ignoreCase = true) ||
                        dish.description.contains(searchPhrase, ignoreCase = true)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        TopAppBar(navController = navController)
        UpperPanel(searchPhrase = searchPhrase, onSearchChange = { searchPhrase = it })
        LowerPanel(navController = navController, dishes = dishes)
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    // Preview without navController and database
    LittleLemonTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(text = "Home Screen Preview")
        }
    }
}
