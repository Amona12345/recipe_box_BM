package com.example.recipebox.domain.usecase.recipe

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
data class RecipeUseCases @Inject constructor(
    val addRecipe: AddRecipeUseCase,
    val getRecipes: GetRecipesUseCase,
    val searchRecipes: SearchRecipesUseCase,
    val getRecipeById: GetRecipeByIdUseCase,
    val deleteRecipe: DeleteRecipeUseCase,
    val updateRecipe: UpdateRecipeUseCase
)