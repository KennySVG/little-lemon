package com.example.littlelemon

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.littlelemon.ui.theme.*

@Composable
fun LowerPanel(navController: NavHostController, dishes: List<Dish> = listOf()) {
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    
    // Get unique categories
    val categories = remember(dishes) {
        listOf("All") + dishes.map { it.category }.distinct().sorted()
    }
    
    // Filter dishes by selected category
    val filteredDishes = remember(dishes, selectedCategory) {
        if (selectedCategory == null || selectedCategory == "All") {
            dishes
        } else {
            dishes.filter { it.category == selectedCategory }
        }
    }
    
    Column {
        Text(
            text = "ORDER FOR DELIVERY!",
            fontFamily = Karla,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = DarkGrey,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        )
        
        // Category filter buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                FilterChip(
                    selected = (selectedCategory == null && category == "All") || selectedCategory == category,
                    onClick = {
                        selectedCategory = if (category == "All") null else category
                    },
                    label = {
                        Text(
                            text = category.replaceFirstChar { it.uppercaseChar() },
                            fontFamily = Karla,
                            fontSize = 14.sp
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PrimaryGreen,
                        selectedLabelColor = White,
                        containerColor = LightGrey.copy(alpha = 0.3f),
                        labelColor = DarkGrey
                    )
                )
            }
        }
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredDishes) { dish ->
                MenuDish(navController, dish)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuDish(navController: NavHostController? = null, dish: Dish) {
    Card(
        onClick = {
            navController?.navigate("${DishDetails.route}/${dish.id}")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = dish.name,
                    fontFamily = Karla,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGrey
                )
                Text(
                    text = dish.description,
                    fontFamily = Karla,
                    fontSize = 14.sp,
                    color = MediumGrey,
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .padding(vertical = 5.dp)
                )
                Text(
                    text = "$${String.format("%.2f", dish.price)}",
                    fontFamily = Karla,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGrey
                )
            }
            AsyncImage(
                model = dish.image,
                contentDescription = dish.description,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        }
    }
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        color = SecondaryYellow,
        thickness = 1.dp
    )
}
