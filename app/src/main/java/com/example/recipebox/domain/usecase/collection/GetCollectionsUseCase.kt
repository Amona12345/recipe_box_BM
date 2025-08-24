package com.example.recipebox.domain.usecase.collection

import com.example.recipebox.domain.repository.CollectionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.recipebox.data.entities.Collection
import com.example.recipebox.domain.model.CollectionModel
import javax.inject.Singleton

@Singleton
class GetCollectionsUseCase @Inject constructor(
    private val repository: CollectionRepository
) {
    operator fun invoke(): Flow<List<CollectionModel>> = repository.getAllCollections()
}