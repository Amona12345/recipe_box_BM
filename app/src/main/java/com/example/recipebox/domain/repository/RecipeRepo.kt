package com.example.recipebox.domain.repository

import com.example.recipebox.domain.model.Ingredient
import com.example.recipebox.domain.model.Recipe
import kotlinx.coroutines.flow.Flow


interface RecipeRepo {
    suspend fun addRecipe(recipe: Recipe, ingredients: List<Ingredient>)
    fun getRecipes(): Flow<List<Recipe>>
}
