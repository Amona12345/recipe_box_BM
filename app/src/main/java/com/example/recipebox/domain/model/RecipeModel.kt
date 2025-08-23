package com.example.recipebox.domain.model

import com.example.recipebox.core.enums.DifficultyEnum


data class RecipeModel(
    val id: Int = 0,
    val title: String,
    val imageUri: String?,
    val tags: List<String>,
    val ingredients: List<IngredientModel>,
    val steps: List<String>,
    val servings: Int = 1,
    val cookingTime: String = "",
    val difficulty: DifficultyEnum = DifficultyEnum.EASY
)