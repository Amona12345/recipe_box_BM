package com.example.recipebox.domain.usecase.collection

import com.example.recipebox.domain.repository.CollectionRepository
import javax.inject.Inject
import com.example.recipebox.data.entities.Collection
import com.example.recipebox.domain.model.CollectionModel
import javax.inject.Singleton

@Singleton
class CreateCollectionUseCase @Inject constructor(
    private val repository: CollectionRepository
) {
    suspend operator fun invoke(name: String): Long {
        if (name.isBlank()) {
            throw IllegalArgumentException("Collection name cannot be empty")
        }
        val collection = CollectionModel(name = name.trim())
        return repository.insertCollection(collection)
    }
}