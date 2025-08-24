package com.example.recipebox.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.recipebox.data.entities.Collection
import com.example.recipebox.data.entities.CollectionRecipeCrossRef
import com.example.recipebox.data.relations.CollectionWithRecipes
import kotlinx.coroutines.flow.Flow
@Dao
interface CollectionDao {

    @Query("SELECT * FROM collections ORDER BY createdAt DESC")
    fun getAllCollections(): Flow<List<Collection>>

    @Query("SELECT * FROM collections WHERE id = :id")
    suspend fun getById(id: Int): Collection?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collection: Collection): Long

    @Update
    suspend fun update(collection: Collection)

    @Delete
    suspend fun delete(collection: Collection)

    @Query("DELETE FROM collections WHERE id = :id")
    suspend fun deleteById(id: Int)

    // CRITICAL: These queries MUST use @Transaction for many-to-many relationships
    @Transaction
    @Query("SELECT * FROM collections ORDER BY createdAt DESC")
    fun getAllCollectionsWithRecipes(): Flow<List<CollectionWithRecipes>>

    @Transaction
    @Query("SELECT * FROM collections WHERE id = :collectionId")
    suspend fun getCollectionWithRecipes(collectionId: Int): CollectionWithRecipes?

    // CRITICAL: Also add a Flow version for single collection
    @Transaction
    @Query("SELECT * FROM collections WHERE id = :collectionId")
    fun getCollectionWithRecipesFlow(collectionId: Int): Flow<CollectionWithRecipes?>

    // Cross-reference operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipeToCollection(crossRef: CollectionRecipeCrossRef)

    @Query("DELETE FROM collection_recipe_cross_ref WHERE collectionId = :collectionId AND recipeId = :recipeId")
    suspend fun removeRecipeFromCollection(collectionId: Int, recipeId: Int)

    @Query("DELETE FROM collection_recipe_cross_ref WHERE collectionId = :collectionId")
    suspend fun deleteAllRecipesFromCollection(collectionId: Int)

    @Query("SELECT COUNT(*) FROM collection_recipe_cross_ref WHERE collectionId = :collectionId AND recipeId = :recipeId")
    suspend fun isRecipeInCollection(collectionId: Int, recipeId: Int): Int

    // DEBUG: Add these queries to help debug
    @Query("SELECT * FROM collection_recipe_cross_ref")
    suspend fun getAllCrossRefs(): List<CollectionRecipeCrossRef>

    @Query("SELECT * FROM collection_recipe_cross_ref WHERE collectionId = :collectionId")
    suspend fun getCrossRefsForCollection(collectionId: Int): List<CollectionRecipeCrossRef>
}