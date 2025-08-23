package com.example.recipebox.domain.usecase.recipe

import com.example.recipebox.data.entities.Recipe
import com.example.recipebox.domain.model.RecipeModel
import com.example.recipebox.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    operator fun invoke(query: String?, tags: List<String>? = null): Flow<List<RecipeModel>> =
        repository.searchRecipes(query, tags)
}