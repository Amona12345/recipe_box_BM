package com.example.recipebox.presentation.screens

import android.net.http.SslCertificate.saveState
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.recipebox.presentation.AppScreens
import com.example.recipebox.presentation.items.add_recipe.NavigationBar
import compose.icons.FeatherIcons
import compose.icons.feathericons.Bookmark

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentRoute by navController.currentBackStackEntryAsState()
    val currentDestination = currentRoute?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 12.dp
    ) {
        val navigationItems = listOf(
            Triple(AppScreens.Home.route, Icons.Default.Home, "Home"),
            Triple(AppScreens.Search.route, Icons.Default.Search, "Search"),
            Triple(AppScreens.Collections.route, FeatherIcons.Bookmark, "Saved"),
            Triple(AppScreens.AddRecipe.route, Icons.Default.Add, "Add")
        )

        navigationItems.forEach { (route, icon, label) ->
            NavigationBarItem(
                selected = currentDestination == route,
                onClick = {
                    if (currentDestination != route) {
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                alwaysShowLabel = false
            )
        }
    }
}