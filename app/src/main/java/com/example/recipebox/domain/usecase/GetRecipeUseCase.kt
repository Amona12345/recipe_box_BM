package com.example.recipebox.domain.usecase

import com.example.recipebox.domain.model.Recipe
import com.example.recipebox.domain.repository.RecipeRepo
import kotlinx.coroutines.flow.Flow

class GetRecipeUseCase(private val repo: RecipeRepo) {
    operator fun invoke(): Flow<List<Recipe>> = repo.getRecipes()
}