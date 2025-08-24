package com.example.recipebox.data.repository

import android.util.Log
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

    private val tag = "CollectionRepository"

    override suspend fun insertCollection(collection: CollectionModel): Long {
        Log.d(tag, "Inserting collection: ${collection.name}")
        return collectionDao.insert(collection.toData())
    }

    override suspend fun updateCollection(collection: CollectionModel) {
        Log.d(tag, "Updating collection: ${collection.name}")
        collectionDao.update(collection.toData())
    }

    override suspend fun deleteCollection(collection: CollectionModel) {
        Log.d(tag, "Deleting collection: ${collection.name}")
        collectionDao.deleteAllRecipesFromCollection(collection.id)
        collectionDao.delete(collection.toData())
    }

    override suspend fun getCollectionById(id: Int): CollectionModel? {
        Log.d(tag, "Getting collection by id: $id")
        return collectionDao.getById(id)?.toDomain()
    }

    override fun getAllCollections(): Flow<List<CollectionModel>> {
        Log.d(tag, "Getting all collections")
        return collectionDao.getAllCollections().map { collections ->
            Log.d(tag, "Collections count: ${collections.size}")
            collections.map { it.toDomain() }
        }
    }

    override fun getAllCollectionsWithRecipes(): Flow<List<CollectionWithRecipesModel>> {
        Log.d(tag, "Getting all collections with recipes")
        return collectionDao.getAllCollectionsWithRecipes().map { collectionsWithRecipes ->
            Log.d(tag, "Collections with recipes count: ${collectionsWithRecipes.size}")
            collectionsWithRecipes.forEach { cwr ->
                Log.d(tag, "Collection '${cwr.collection.name}' has ${cwr.recipes.size} recipes")
            }
            collectionsWithRecipes.map { it.toDomain() }
        }
    }

    override suspend fun getCollectionWithRecipes(collectionId: Int): CollectionWithRecipesModel? {
        Log.d(tag, "Getting collection with recipes for id: $collectionId")
        val result = collectionDao.getCollectionWithRecipes(collectionId)?.toDomain()
        Log.d(tag, "Collection result: ${result?.collection?.name}, recipes count: ${result?.recipes?.size}")
        return result
    }

    override fun getCollectionWithRecipesFlow(collectionId: Int): Flow<CollectionWithRecipesModel?> {
        Log.d(tag, "Getting collection with recipes flow for id: $collectionId")
        return collectionDao.getCollectionWithRecipesFlow(collectionId).map { collectionWithRecipes ->
            collectionWithRecipes?.toDomain()?.also { result ->
                Log.d(tag, "Flow update - Collection: ${result.collection.name}, recipes: ${result.recipes.size}")
            }
        }
    }

    override suspend fun addRecipeToCollection(collectionId: Int, recipeId: Int) {
        Log.d(tag, "Adding recipe $recipeId to collection $collectionId")

        val exists = collectionDao.isRecipeInCollection(collectionId, recipeId) > 0
        Log.d(tag, "Recipe already in collection: $exists")

        if (!exists) {
            val crossRef = CollectionRecipeCrossRef(collectionId, recipeId)
            collectionDao.addRecipeToCollection(crossRef)
            Log.d(tag, "Cross reference added successfully")

            val newExists = collectionDao.isRecipeInCollection(collectionId, recipeId) > 0
            Log.d(tag, "Recipe now in collection: $newExists")
        } else {
            Log.d(tag, "Recipe already exists in collection, skipping")
            throw IllegalArgumentException("Recipe is already in this collection")
        }
    }

    override suspend fun removeRecipeFromCollection(collectionId: Int, recipeId: Int) {
        Log.d(tag, "Removing recipe $recipeId from collection $collectionId")
        collectionDao.removeRecipeFromCollection(collectionId, recipeId)

        val exists = collectionDao.isRecipeInCollection(collectionId, recipeId) > 0
        Log.d(tag, "Recipe still exists after removal: $exists")
    }

    override suspend fun deleteCollectionById(id: Int) {
        Log.d(tag, "Deleting collection by id: $id")
        collectionDao.deleteAllRecipesFromCollection(id)
        collectionDao.deleteById(id)
    }
}