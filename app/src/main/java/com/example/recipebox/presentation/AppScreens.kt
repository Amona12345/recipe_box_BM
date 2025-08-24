package com.example.recipebox.presentation

sealed class AppScreens(val route: String) {
    object Splash : AppScreens("splash")
    object Onboarding : AppScreens("onboarding")
    object Home : AppScreens("home")
    object Search : AppScreens("search")
    object Collections : AppScreens("collections")
    object AddRecipe : AppScreens("add_recipe")


}