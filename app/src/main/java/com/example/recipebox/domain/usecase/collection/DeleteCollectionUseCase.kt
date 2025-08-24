package com.example.recipebox.domain.usecase.collection

import com.example.recipebox.domain.repository.CollectionRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteCollectionUseCase @Inject constructor(
    private val repository: CollectionRepository
) {
    suspend operator fun invoke(collectionId: Int) {
        val collection = repository.getCollectionById(collectionId)
            ?: throw IllegalArgumentException("Collection not found")
        repository.deleteCollection(collection)
    }
}
