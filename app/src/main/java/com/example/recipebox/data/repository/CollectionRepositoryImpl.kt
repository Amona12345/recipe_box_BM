package com.example.recipebox.data.repository

import com.example.recipebox.data.dao.CollectionDao
import com.example.recipebox.domain.repository.CollectionRepository
import javax.inject.Inject
import javax.inject.Singleton
import com.example.recipebox.data.entities.Collection
import com.example.recipebox.data.entities.CollectionRecipeCrossRef
import com.example.recipebox.data.relations.CollectionWithRecipes
import com.example.recipebox.domain.model.CollectionModel
import com.example.recipebox.domain.model.CollectionWithRecipesModel
import com.example.recipebox.domain.model.mappers.toData
import com.example.recipebox.domain.model.mappers.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


@Singleton
class CollectionRepositoryImpl @Inject constructor(
    private val collectionDao: CollectionDao
) : CollectionRepository {

    override suspend fun insertCollection(collection: CollectionModel): Long =
        collectionDao.insert(collection.toData())

    override suspend fun updateCollection(collection: CollectionModel) =
        collectionDao.update(collection.toData())

    override suspend fun deleteCollection(collection: CollectionModel) =
        collectionDao.delete(collection.toData())

    override suspend fun getCollectionById(id: Int): CollectionModel? =
        collectionDao.getById(id)?.toDomain()

    override fun getAllCollections(): Flow<List<CollectionModel>> =
        collectionDao.getAllCollections().map { collections ->
            collections.map { it.toDomain() }
        }

    override fun getAllCollectionsWithRecipes(): Flow<List<CollectionWithRecipesModel>> =
        collectionDao.getAllCollectionsWithRecipes().map { collectionsWithRecipes ->
            collectionsWithRecipes.map { it.toDomain() }
        }

    override suspend fun getCollectionWithRecipes(collectionId: Int): CollectionWithRecipesModel? =
        collectionDao.getCollectionWithRecipes(collectionId)?.toDomain()

    override suspend fun addRecipeToCollection(collectionId: Int, recipeId: Int) {
        collectionDao.addRecipeToCollection(CollectionRecipeCrossRef(collectionId, recipeId))
    }

    override suspend fun removeRecipeFromCollection(collectionId: Int, recipeId: Int) =
        collectionDao.removeRecipeFromCollection(collectionId, recipeId)

    override suspend fun deleteCollectionById(id: Int) {
        collectionDao.deleteAllRecipesFromCollection(id)
        collectionDao.deleteById(id)
    }
}