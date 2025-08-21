package com.example.recipebox.domain.usecase

import com.example.recipebox.domain.model.Ingredient
import com.example.recipebox.domain.model.Recipe
import com.example.recipebox.domain.repository.RecipeRepo
import javax.inject.Inject

class AddRecipeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepo
) {
    suspend operator fun invoke(recipe: Recipe, ingredients: List<Ingredient>) {
        recipeRepository.addRecipe(recipe, ingredients)
    }

}