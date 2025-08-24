package com.example.recipebox.domain.usecase.collection

import com.example.recipebox.domain.repository.CollectionRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCollectionWithRecipesFlow@Inject constructor(    private val repository: CollectionRepository){
    suspend operator fun invoke(collectionId: Int) =
        repository.getCollectionWithRecipesFlow(collectionId)

}