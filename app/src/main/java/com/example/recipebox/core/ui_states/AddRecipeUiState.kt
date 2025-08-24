package com.example.recipebox.core.ui_states

import com.example.recipebox.core.enums.DifficultyEnum
import com.example.recipebox.domain.model.IngredientModel

data class AddRecipeUiState(
    val title: String = "",
    val description: String = "",
    val imageUri: String? = null,
    val localImagePath: String? = null,
    val ingredients: List<IngredientModel> = listOf(IngredientModel("", "", "")),
    val steps: List<String> = listOf(""),
    val tags: List<String> = emptyList(),
    val servings: Int = 4,
    val prepTime: String = "",
    val cookTime: String = "",
    val difficulty: DifficultyEnum = DifficultyEnum.EASY,
    val dishType: String = "",
    val dietaryTags: List<String> = emptyList(),
    val cuisine: String = "",
    val calories: String = "",
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val errorMessage: String? = null,
    val currentStep: Int = 0,
    val showImagePickerDialog: Boolean = false
)
