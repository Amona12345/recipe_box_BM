package com.example.recipebox.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.recipebox.data.entities.Recipe
import com.example.recipebox.data.relations.RecipeWithCollections
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe): Long

    @Update
    suspend fun update(recipe: Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)

    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getById(id: Int): Recipe?

    @Query("SELECT * FROM recipes ORDER BY title ASC")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Query("""
        SELECT * FROM recipes 
        WHERE (:searchQuery IS NULL OR :searchQuery = '' OR 
               title LIKE '%' || :searchQuery || '%' OR 
               ingredients LIKE '%' || :searchQuery || '%')
        AND (:selectedTags IS NULL OR :selectedTags = '' OR 
             tags LIKE '%' || :selectedTags || '%')
        ORDER BY title ASC
    """)
    fun searchRecipes(searchQuery: String?, selectedTags: String?): Flow<List<Recipe>>

    @Query("DELETE FROM recipes WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Transaction
    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    suspend fun getRecipeWithCollections(recipeId: Int): RecipeWithCollections?
}