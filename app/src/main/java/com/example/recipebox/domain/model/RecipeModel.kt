package com.example.recipebox.domain.model

import com.example.recipebox.core.enums.DifficultyEnum


data class RecipeModel(
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val imageUri: String?,
    val localImagePath: String? = null,
    val tags: List<String>,
    val ingredients: List<IngredientModel>,
    val steps: List<String>,
    val servings: Int = 1,
    val prepTime: String = "",
    val cookTime: String = "",
    val totalTime: String = "",
    val difficulty: DifficultyEnum = DifficultyEnum.EASY,
    val dishType: String = "",
    val dietaryTags: List<String> = emptyList(),
    val cuisine: String = "",
    val calories: Int? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
