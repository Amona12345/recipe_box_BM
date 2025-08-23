package com.example.recipebox.core.ui_states

import com.example.recipebox.core.enums.DifficultyEnum
import com.example.recipebox.domain.model.IngredientModel

data class AddRecipeUiState(
    val title: String = "",
    val imageUri: String? = null,
    val ingredients: List<IngredientModel> = listOf(IngredientModel("", "", "")),
    val steps: List<String> = listOf(""),
    val tags: List<String> = emptyList(),
    val servings: Int = 1,
    val cookingTime: String = "",
    val difficulty: DifficultyEnum = DifficultyEnum.EASY,
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val errorMessage: String? = null
)