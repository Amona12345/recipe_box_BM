package com.example.recipebox.domain.repository

import com.example.recipebox.data.entities.Recipe
import com.example.recipebox.data.relations.RecipeWithCollections
import com.example.recipebox.domain.model.RecipeModel
import com.example.recipebox.domain.model.RecipeWithCollectionsModel
import kotlinx.coroutines.flow.Flow



interface RecipeRepository {
    suspend fun insertRecipe(recipe: RecipeModel): Long
    suspend fun updateRecipe(recipe: RecipeModel)
    suspend fun deleteRecipe(recipe: RecipeModel)
    suspend fun getRecipeById(id: Int): RecipeModel?
    fun getAllRecipes(): Flow<List<RecipeModel>>
    fun searchRecipes(query: String?, tags: List<String>?): Flow<List<RecipeModel>>
    suspend fun getRecipeWithCollections(recipeId: Int): RecipeWithCollectionsModel?
}