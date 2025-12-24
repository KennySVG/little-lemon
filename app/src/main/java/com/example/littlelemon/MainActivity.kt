package com.example.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    private val httpClient = HttpClient(Android)
    
    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "database"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Fetch menu data from network
        CoroutineScope(Dispatchers.IO).launch {
            try {
                android.util.Log.d("MainActivity", "Fetching menu data from network...")
                val response = httpClient.get(
                    "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
                )
                val jsonString = response.bodyAsText()
                android.util.Log.d("MainActivity", "Received JSON string: ${jsonString.take(200)}...")
                
                val menuNetwork: MenuNetwork = json.decodeFromString(jsonString)

                android.util.Log.d("MainActivity", "Received ${menuNetwork.menu.size} menu items")

                // Map network data to Room entities
                val menuItemsRoom = menuNetwork.menu.map { menuItem ->
                    MenuItemRoom(
                        id = menuItem.id,
                        title = menuItem.title,
                        description = menuItem.description,
                        price = menuItem.price,
                        image = menuItem.image,
                        category = menuItem.category
                    )
                }

                // Save to database
                database.menuDao().insertAll(menuItemsRoom)
                android.util.Log.d("MainActivity", "Saved ${menuItemsRoom.size} items to database")
            } catch (e: Exception) {
                android.util.Log.e("MainActivity", "Error fetching menu data", e)
                e.printStackTrace()
            }
        }

        setContent {
            LittleLemonTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.systemBars),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    MyNavigation(navController = navController, database = database)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        httpClient.close()
    }
}