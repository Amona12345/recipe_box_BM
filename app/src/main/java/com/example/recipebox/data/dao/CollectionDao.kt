package com.example.recipebox.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.recipebox.data.entities.Collection
import com.example.recipebox.data.entities.CollectionRecipeCrossRef
import com.example.recipebox.data.entities.CollectionWithRecipes
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collection: Collection): Long

    @Delete
    suspend fun delete(collection: Collection)
    @Query("SELECT * FROM collections ORDER BY name ASC")
    fun getCollections(): Flow<List<Collection>>

    @Transaction
    @Query("SELECT * FROM collections WHERE id = :id")
    fun getCollectionWithRecipes(id: Long): Flow<CollectionWithRecipes?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRecipeToCollection(crossRef: CollectionRecipeCrossRef)

    @Query("DELETE FROM CollectionRecipeCrossRef WHERE collectionId = :collectionId AND recipeId = :recipeId")
    suspend fun removeRecipeFromCollection(collectionId: Long, recipeId: Long)

    @Query("DELETE FROM collections WHERE id = :id")
    suspend fun deleteById(id: Long)
}