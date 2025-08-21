package com.example.recipebox.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipebox.data.entities.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(recipe: Recipe): Long

    @Delete
    suspend fun delete(recipe: Recipe)

    @Query("""
        SELECT * FROM recipes
        WHERE (:title IS NULL OR title LIKE '%' || :title || '%')
    """)
    fun searchByTitle(title: String?): Flow<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getById(id: Long): Flow<Recipe?>

    @Query("SELECT * FROM recipes")
    fun getAll(): Flow<List<Recipe>>

    @Query("DELETE FROM recipes WHERE id = :id")
    suspend fun deleteById(id: Long)
}
