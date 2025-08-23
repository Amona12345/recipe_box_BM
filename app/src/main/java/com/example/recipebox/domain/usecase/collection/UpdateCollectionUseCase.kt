package com.example.recipebox.domain.usecase.collection

import com.example.recipebox.domain.repository.CollectionRepository
import javax.inject.Inject
import com.example.recipebox.data.entities.Collection
import com.example.recipebox.domain.model.CollectionModel


class UpdateCollectionUseCase @Inject constructor(
    private val repository: CollectionRepository
) {
    suspend operator fun invoke(collection: CollectionModel) = repository.updateCollection(collection)
}
