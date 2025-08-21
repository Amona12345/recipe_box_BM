package com.example.recipebox.data.repository

import com.example.recipebox.data.dao.RecipeDao
import com.example.recipebox.domain.model.Ingredient
import com.example.recipebox.domain.model.Recipe
import com.example.recipebox.domain.repository.RecipeRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao
)  : RecipeRepo {
    override suspend fun addRecipe(
        recipe: Recipe,
        ingredients: List<Ingredient>
    ) {
        TODO("Not yet implemented")
    }

    override fun getRecipes(): Flow<List<Recipe>> {
        TODO("Not yet implemented")
    }


}