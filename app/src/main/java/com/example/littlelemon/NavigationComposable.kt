package com.example.littlelemon

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun MyNavigation(navController: NavHostController, database: AppDatabase) {
    val context = LocalContext.current
    val prefs = remember { UserPreferences(context) }
    
    val startDestination = if (prefs.isOnboardingComplete()) {
        Home.route
    } else {
        Onboarding.route
    }
    
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Onboarding.route) {
            OnboardingScreen(
                navController = navController
            )
        }
        composable(route = Home.route) {
            Home(navController = navController, database = database)
        }
        composable(route = Profile.route) {
            Profile(navController = navController)
        }
        composable(
            route = "${DishDetails.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            DishDetails(
                navController = navController,
                id = id,
                database = database
            )
        }
    }
}

