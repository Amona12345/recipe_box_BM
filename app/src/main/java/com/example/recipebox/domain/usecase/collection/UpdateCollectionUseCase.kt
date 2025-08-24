package com.example.recipebox.domain.usecase.collection

import com.example.recipebox.domain.repository.CollectionRepository
import javax.inject.Inject
import com.example.recipebox.data.entities.Collection
import com.example.recipebox.domain.model.CollectionModel
import javax.inject.Singleton


@Singleton
class UpdateCollectionUseCase @Inject constructor(
    private val repository: CollectionRepository
) {
    suspend operator fun invoke(collection: CollectionModel) {
        if (collection.name.isBlank()) {
            throw IllegalArgumentException("Collection name cannot be empty")
        }
        repository.updateCollection(collection)
    }
}