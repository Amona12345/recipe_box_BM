package com.example.recipebox.domain.usecase.recipe

data class RecipeUseCases(
    val addRecipe: AddRecipeUseCase,
    val getRecipes: GetRecipesUseCase,
    val searchRecipes: SearchRecipesUseCase,
    val getRecipeById: GetRecipeByIdUseCase,
    val deleteRecipe: DeleteRecipeUseCase,
    val updateRecipe: UpdateRecipeUseCase
)
