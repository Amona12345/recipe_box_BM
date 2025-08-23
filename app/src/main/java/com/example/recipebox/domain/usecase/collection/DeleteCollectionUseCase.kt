package com.example.recipebox.domain.usecase.collection

import com.example.recipebox.domain.repository.CollectionRepository
import javax.inject.Inject

class DeleteCollectionUseCase @Inject constructor(
    private val repository: CollectionRepository
) {
    suspend operator fun invoke(collectionId: Int) = repository.deleteCollectionById(collectionId)
}