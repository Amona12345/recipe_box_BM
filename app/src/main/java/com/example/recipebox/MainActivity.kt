package com.example.recipebox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipebox.presentation.screens.SplashScreen
import com.example.recipebox.presentation.screens.OnboardingScreen
import com.example.recipebox.presentation.screens.AddRecipeScreen
import com.example.recipebox.ui.theme.RecipeBoxTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeBoxTheme {
                val navController = rememberNavController()
                Scaffold { paddingValues ->
                    NavigationGraph(
                        navController = navController,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(
                onNavigateToOnboarding = {
                    navController.navigate("onboarding") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable("onboarding") {
            OnboardingScreen(
                onGetStartedClick = {
                    navController.navigate("add_recipe") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            )
        }

        composable("add_recipe") {
            AddRecipeScreen(
                modifier = modifier,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
