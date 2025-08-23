package com.example.recipebox.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.recipebox.core.enums.DifficultyEnum
import com.example.recipebox.data.converters.Converters

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String = "",
    val imageUri: String?,
    val localImagePath: String? = null,
    val tags: List<String>,
    val ingredients: List<String>,
    val steps: List<String>,
    val servings: Int = 1,
    val prepTime: String = "",
    val cookTime: String = "",
    val totalTime: String = "",
    val difficulty: DifficultyEnum = DifficultyEnum.EASY, // Now stores complete enum data
    val dishType: String = "",
    val dietaryTags: List<String> = emptyList(),
    val cuisine: String = "",
    val calories: Int? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)