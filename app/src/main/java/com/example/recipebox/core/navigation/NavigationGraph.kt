package com.example.recipebox.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.recipebox.presentation.AppScreens
import com.example.recipebox.presentation.screens.AddRecipeScreen
import com.example.recipebox.presentation.screens.AddToCollectionScreen
import com.example.recipebox.presentation.screens.CollectionDetailsScreen
import com.example.recipebox.presentation.screens.CollectionsScreen
import com.example.recipebox.presentation.screens.HomeScreen
import com.example.recipebox.presentation.screens.OnboardingScreen
import com.example.recipebox.presentation.screens.RecipeDetailScreen
import com.example.recipebox.presentation.screens.SearchScreen
import com.example.recipebox.presentation.screens.SplashScreen
@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = AppScreens.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // Splash Screen
        composable(AppScreens.Splash.route) {
            SplashScreen(
                onNavigateToOnboarding = {
                    navController.navigate(AppScreens.Onboarding.route) {
                        popUpTo(AppScreens.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // Onboarding Screen
        composable(AppScreens.Onboarding.route) {
            OnboardingScreen(
                onGetStartedClick = {
                    navController.navigate(AppScreens.Home.route) {
                        popUpTo(AppScreens.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(AppScreens.Home.route) {
            HomeScreen(
                onRecipeClick = { recipeId ->
                    navController.navigate("recipe_detail/$recipeId")
                },
                onSeeAllClick = {
                    navController.navigate(AppScreens.Search.route)
                }
            )
        }

        composable(AppScreens.Search.route) {
            SearchScreen(
                onRecipeClick = { recipeId ->
                    navController.navigate("recipe_detail/$recipeId")
                }
            )
        }

        composable(AppScreens.Collections.route) {
            CollectionsScreen(
                onCollectionClick = { collectionId ->
                    navController.navigate("collection_details/$collectionId")
                }
            )
        }

        composable(AppScreens.AddRecipe.route) {
            AddRecipeScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "collection_details/{collectionId}",
            arguments = listOf(navArgument("collectionId") { type = NavType.IntType })
        ) { backStackEntry ->
            val collectionId = backStackEntry.arguments?.getInt("collectionId") ?: 0
            CollectionDetailsScreen(
                collectionId = collectionId,
                onBackClick = {
                    navController.popBackStack()
                },
                onAddRecipeToCollection = { collectionId ->
                    navController.navigate("add_recipe_to_collection/$collectionId")
                }
            )
        }

        composable(
            route = "add_recipe_to_collection/{collectionId}",
            arguments = listOf(navArgument("collectionId") { type = NavType.IntType })
        ) { backStackEntry ->
            val collectionId = backStackEntry.arguments?.getInt("collectionId") ?: 0
            AddToCollectionScreen(
                collectionId = collectionId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "recipe_detail/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: 0
            RecipeDetailScreen(
                recipeId = recipeId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}