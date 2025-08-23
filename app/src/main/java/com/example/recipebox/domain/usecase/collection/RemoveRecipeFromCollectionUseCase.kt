package com.example.recipebox.domain.usecase.collection

import com.example.recipebox.domain.repository.CollectionRepository
import javax.inject.Inject

class RemoveRecipeFromCollectionUseCase @Inject constructor(
    private val repository: CollectionRepository
) {
    suspend operator fun invoke(collectionId: Int, recipeId: Int) =
        repository.removeRecipeFromCollection(collectionId, recipeId)
}