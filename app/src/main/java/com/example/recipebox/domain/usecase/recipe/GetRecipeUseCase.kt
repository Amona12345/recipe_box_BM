package com.example.recipebox.domain.usecase.recipe

import com.example.recipebox.data.entities.Recipe
import com.example.recipebox.domain.model.RecipeModel
import com.example.recipebox.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    operator fun invoke(): Flow<List<RecipeModel>> = repository.getAllRecipes()
}