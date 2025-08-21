package com.example.recipebox.domain.usecase

import com.example.recipebox.domain.model.Ingredient
import com.example.recipebox.domain.model.Recipe
import com.example.recipebox.domain.repository.RecipeRepo

class AddRecipeUseCase(private val repo: RecipeRepo) {
    suspend operator fun invoke(recipe: Recipe, ingredients: List<Ingredient>) {
        repo.addRecipe(recipe, ingredients)
    }

}