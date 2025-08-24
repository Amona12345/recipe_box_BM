package com.example.recipebox.domain.usecase.collection

import com.example.recipebox.data.relations.CollectionWithRecipes
import com.example.recipebox.domain.model.CollectionWithRecipesModel
import com.example.recipebox.domain.repository.CollectionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton



@Singleton
class GetCollectionsWithRecipesUseCase @Inject constructor(
    private val repository: CollectionRepository
) {
    operator fun invoke(): Flow<List<CollectionWithRecipesModel>> =
        repository.getAllCollectionsWithRecipes()
}