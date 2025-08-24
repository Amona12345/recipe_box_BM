package com.example.recipebox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.recipebox.core.navigation.NavigationGraph
import com.example.recipebox.presentation.AppScreens
import com.example.recipebox.presentation.screens.SplashScreen
import com.example.recipebox.presentation.screens.OnboardingScreen
import com.example.recipebox.presentation.screens.AddRecipeScreen
import com.example.recipebox.presentation.screens.BottomNavigationBar
import com.example.recipebox.presentation.screens.CollectionsScreen
import com.example.recipebox.ui.theme.RecipeBoxTheme
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeBoxTheme {
                val navController = rememberNavController()
                val currentRoute by navController.currentBackStackEntryAsState()
                val currentDestination = currentRoute?.destination?.route

                val hideBottomNavRoutes = setOf(
                    AppScreens.Splash.route,
                    AppScreens.Onboarding.route,
                    AppScreens.AddRecipe.route // Add this if you want full edge-to-edge for AddRecipe
                )

                val shouldShowBottomNav = currentDestination !in hideBottomNavRoutes

                Scaffold(
                    bottomBar = {
                        if (shouldShowBottomNav) {
                            BottomNavigationBar(navController)
                        }
                    }
                ) { paddingValues ->
                    NavigationGraph(
                        navController = navController,
                        modifier = if (shouldShowBottomNav) {
                            Modifier.padding(
                                bottom = paddingValues.calculateBottomPadding()
                            )
                        } else {
                            Modifier
                        }
                    )
                }
            }
        }
    }
}