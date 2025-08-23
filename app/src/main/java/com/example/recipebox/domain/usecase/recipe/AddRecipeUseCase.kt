package com.example.recipebox.domain.usecase.recipe

import com.example.recipebox.data.entities.Recipe
import com.example.recipebox.domain.model.RecipeModel
import com.example.recipebox.domain.repository.RecipeRepository
import javax.inject.Inject


class AddRecipeUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(recipe: RecipeModel): Long = repository.insertRecipe(recipe)
}