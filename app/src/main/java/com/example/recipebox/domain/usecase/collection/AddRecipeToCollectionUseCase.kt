package com.example.recipebox.domain.usecase.collection

import com.example.recipebox.domain.repository.CollectionRepository
import com.example.recipebox.domain.repository.RecipeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddRecipeToCollectionUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository,
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(collectionId: Int, recipeId: Int) {
        // Validate that both collection and recipe exist
        val collection = collectionRepository.getCollectionById(collectionId)
            ?: throw IllegalArgumentException("Collection not found")

        val recipe = recipeRepository.getRecipeById(recipeId)
            ?: throw IllegalArgumentException("Recipe not found")

        // Check if recipe is already in collection
        val currentCollection = collectionRepository.getCollectionWithRecipes(collectionId)
        val recipeAlreadyExists = currentCollection?.recipes?.any { it.id == recipeId } == true

        if (recipeAlreadyExists) {
            throw IllegalArgumentException("Recipe is already in this collection")
        }

        collectionRepository.addRecipeToCollection(collectionId, recipeId)
    }
}

