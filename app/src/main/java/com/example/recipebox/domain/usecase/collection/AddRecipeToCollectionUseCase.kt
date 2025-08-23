package com.example.recipebox.domain.usecase.collection

import com.example.recipebox.domain.repository.CollectionRepository
import javax.inject.Inject

class AddRecipeToCollectionUseCase @Inject constructor(
    private val repository: CollectionRepository
) {
    suspend operator fun invoke(collectionId: Int, recipeId: Int) =
        repository.addRecipeToCollection(collectionId, recipeId)
}
