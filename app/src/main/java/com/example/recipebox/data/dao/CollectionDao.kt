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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collection: Collection): Long

    @Update
    suspend fun update(collection: Collection)

    @Delete
    suspend fun delete(collection: Collection)

    @Query("SELECT * FROM collections ORDER BY name ASC")
    fun getAllCollections(): Flow<List<Collection>>

    @Query("SELECT * FROM collections WHERE id = :id")
    suspend fun getById(id: Int): Collection?

    @Transaction
    @Query("SELECT * FROM collections WHERE id = :collectionId")
    suspend fun getCollectionWithRecipes(collectionId: Int): CollectionWithRecipes?

    @Transaction
    @Query("SELECT * FROM collections ORDER BY name ASC")
    fun getAllCollectionsWithRecipes(): Flow<List<CollectionWithRecipes>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRecipeToCollection(crossRef: CollectionRecipeCrossRef)

    @Query("DELETE FROM collection_recipe_cross_ref WHERE collectionId = :collectionId AND recipeId = :recipeId")
    suspend fun removeRecipeFromCollection(collectionId: Int, recipeId: Int)

    @Query("DELETE FROM collections WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM collection_recipe_cross_ref WHERE collectionId = :collectionId")
    suspend fun deleteAllRecipesFromCollection(collectionId: Int)
}