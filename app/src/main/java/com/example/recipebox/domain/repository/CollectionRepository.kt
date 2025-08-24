package com.example.recipebox.domain.repository

import com.example.recipebox.data.relations.CollectionWithRecipes
import kotlinx.coroutines.flow.Flow
import com.example.recipebox.data.entities.Collection
import com.example.recipebox.domain.model.CollectionModel
import com.example.recipebox.domain.model.CollectionWithRecipesModel

interface CollectionRepository {
    suspend fun insertCollection(collection: CollectionModel): Long
    suspend fun updateCollection(collection: CollectionModel)
    suspend fun deleteCollection(collection: CollectionModel)
    suspend fun getCollectionById(id: Int): CollectionModel?
    fun getAllCollections(): Flow<List<CollectionModel>>
    fun getAllCollectionsWithRecipes(): Flow<List<CollectionWithRecipesModel>>
    suspend fun getCollectionWithRecipes(collectionId: Int): CollectionWithRecipesModel?

    fun getCollectionWithRecipesFlow(collectionId: Int): Flow<CollectionWithRecipesModel?>

    suspend fun addRecipeToCollection(collectionId: Int, recipeId: Int)
    suspend fun removeRecipeFromCollection(collectionId: Int, recipeId: Int)
    suspend fun deleteCollectionById(id: Int)
}