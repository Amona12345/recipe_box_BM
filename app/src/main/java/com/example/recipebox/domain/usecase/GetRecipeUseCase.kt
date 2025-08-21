package com.example.recipebox.domain.usecase

import com.example.recipebox.domain.model.Recipe
import com.example.recipebox.domain.repository.RecipeRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecipeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepo
){
    operator fun invoke(): Flow<List<Recipe>> = recipeRepository.getRecipes()
}