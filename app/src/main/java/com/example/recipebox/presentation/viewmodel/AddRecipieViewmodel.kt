package com.example.recipebox.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebox.core.enums.DifficultyEnum
import com.example.recipebox.core.ui_states.AddRecipeUiState
import com.example.recipebox.domain.model.IngredientModel
import com.example.recipebox.domain.model.RecipeModel
import com.example.recipebox.domain.model.mappers.formatIngredientString
import com.example.recipebox.domain.model.mappers.parseIngredientString
import com.example.recipebox.domain.usecase.recipe.RecipeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddRecipeViewModel @Inject constructor(
    private val recipeUseCases: RecipeUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddRecipeUiState())
    val uiState: StateFlow<AddRecipeUiState> = _uiState.asStateFlow()

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun updateImageUri(uri: String?) {
        _uiState.value = _uiState.value.copy(imageUri = uri)
    }

    fun updateServings(servings: Int) {
        _uiState.value = _uiState.value.copy(servings = servings)
    }

    fun updateCookingTime(time: String) {
        _uiState.value = _uiState.value.copy(cookingTime = time)
    }

    fun updateDifficulty(difficulty: DifficultyEnum) {
        _uiState.value = _uiState.value.copy(difficulty = difficulty)
    }

    fun updateDifficultyFromString(difficulty: String) {
        val difficultyEnum = when (difficulty.lowercase()) {
            "easy" -> DifficultyEnum.EASY
            "medium" -> DifficultyEnum.MEDIUM
            "hard" -> DifficultyEnum.HARD
            else -> DifficultyEnum.EASY
        }
        _uiState.value = _uiState.value.copy(difficulty = difficultyEnum)
    }

    fun addIngredient(ingredient: IngredientModel = IngredientModel("", "", "")) {
        val currentIngredients = _uiState.value.ingredients.toMutableList()
        currentIngredients.add(ingredient)
        _uiState.value = _uiState.value.copy(ingredients = currentIngredients)
    }

    fun removeIngredient(index: Int) {
        val currentIngredients = _uiState.value.ingredients.toMutableList()
        if (index in currentIngredients.indices && currentIngredients.size > 1) {
            currentIngredients.removeAt(index)
            _uiState.value = _uiState.value.copy(ingredients = currentIngredients)
        }
    }

    fun updateIngredient(index: Int, ingredient: IngredientModel) {
        val currentIngredients = _uiState.value.ingredients.toMutableList()
        if (index in currentIngredients.indices) {
            currentIngredients[index] = ingredient
            _uiState.value = _uiState.value.copy(ingredients = currentIngredients)
        }
    }

    fun updateIngredientName(index: Int, name: String) {
        val currentIngredients = _uiState.value.ingredients.toMutableList()
        if (index in currentIngredients.indices) {
            currentIngredients[index] = currentIngredients[index].copy(name = name)
            _uiState.value = _uiState.value.copy(ingredients = currentIngredients)
        }
    }

    fun updateIngredientAmount(index: Int, amount: String) {
        val currentIngredients = _uiState.value.ingredients.toMutableList()
        if (index in currentIngredients.indices) {
            currentIngredients[index] = currentIngredients[index].copy(amount = amount)
            _uiState.value = _uiState.value.copy(ingredients = currentIngredients)
        }
    }

    fun updateIngredientUnit(index: Int, unit: String) {
        val currentIngredients = _uiState.value.ingredients.toMutableList()
        if (index in currentIngredients.indices) {
            currentIngredients[index] = currentIngredients[index].copy(unit = unit)
            _uiState.value = _uiState.value.copy(ingredients = currentIngredients)
        }
    }

    fun addIngredientFromString(ingredientString: String) {
        val ingredient = parseIngredientString(ingredientString)
        addIngredient(ingredient)
    }

    fun updateIngredientFromString(index: Int, ingredientString: String) {
        val ingredient = parseIngredientString(ingredientString)
        updateIngredient(index, ingredient)
    }

    fun addStep(step: String = "") {
        val currentSteps = _uiState.value.steps.toMutableList()
        currentSteps.add(step)
        _uiState.value = _uiState.value.copy(steps = currentSteps)
    }

    fun removeStep(index: Int) {
        val currentSteps = _uiState.value.steps.toMutableList()
        if (index in currentSteps.indices && currentSteps.size > 1) {
            currentSteps.removeAt(index)
            _uiState.value = _uiState.value.copy(steps = currentSteps)
        }
    }

    fun updateStep(index: Int, step: String) {
        val currentSteps = _uiState.value.steps.toMutableList()
        if (index in currentSteps.indices) {
            currentSteps[index] = step
            _uiState.value = _uiState.value.copy(steps = currentSteps)
        }
    }

    fun addTag(tag: String) {
        val trimmedTag = tag.trim()
        if (trimmedTag.isNotBlank()) {
            val currentTags = _uiState.value.tags.toMutableList()
            if (!currentTags.contains(trimmedTag)) {
                currentTags.add(trimmedTag)
                _uiState.value = _uiState.value.copy(tags = currentTags)
            }
        }
    }

    fun removeTag(tag: String) {
        val currentTags = _uiState.value.tags.toMutableList()
        currentTags.remove(tag)
        _uiState.value = _uiState.value.copy(tags = currentTags)
    }

    fun removeTagAt(index: Int) {
        val currentTags = _uiState.value.tags.toMutableList()
        if (index in currentTags.indices) {
            currentTags.removeAt(index)
            _uiState.value = _uiState.value.copy(tags = currentTags)
        }
    }

    fun isFormValid(): Boolean {
        val state = _uiState.value
        return state.title.isNotBlank() &&
                state.ingredients.any { it.name.isNotBlank() } &&
                state.steps.any { it.isNotBlank() }
    }

    fun getValidationErrors(): List<String> {
        val errors = mutableListOf<String>()
        val state = _uiState.value

        if (state.title.isBlank()) {
            errors.add("Recipe title is required")
        }

        if (state.ingredients.none { it.name.isNotBlank() }) {
            errors.add("At least one ingredient is required")
        }

        if (state.steps.none { it.isNotBlank() }) {
            errors.add("At least one cooking step is required")
        }

        if (state.servings <= 0) {
            errors.add("Servings must be greater than 0")
        }

        return errors
    }

    fun saveRecipe() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)

                if (!isFormValid()) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Please fill in all required fields"
                    )
                    return@launch
                }

                val recipe = RecipeModel(
                    title = _uiState.value.title.trim(),
                    imageUri = _uiState.value.imageUri,
                    ingredients = _uiState.value.ingredients.filter { it.name.isNotBlank() },
                    steps = _uiState.value.steps.filter { it.isNotBlank() },
                    tags = _uiState.value.tags,
                    servings = _uiState.value.servings,
                    cookingTime = _uiState.value.cookingTime.trim(),
                    difficulty = _uiState.value.difficulty
                )

                recipeUseCases.addRecipe(recipe)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isSaved = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Failed to save recipe"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun resetForm() {
        _uiState.value = AddRecipeUiState()
    }

    fun resetSavedState() {
        _uiState.value = _uiState.value.copy(isSaved = false)
    }

    fun getDifficultyOptions(): List<DifficultyEnum> {
        return DifficultyEnum.entries
    }

    fun getDifficultyDisplayName(difficulty: DifficultyEnum): String {
        return when (difficulty) {
            DifficultyEnum.EASY -> "Easy"
            DifficultyEnum.MEDIUM -> "Medium"
            DifficultyEnum.HARD -> "Hard"
        }
    }

    fun canRemoveIngredient(): Boolean {
        return _uiState.value.ingredients.size > 1
    }

    fun canRemoveStep(): Boolean {
        return _uiState.value.steps.size > 1
    }

    fun getIngredientDisplayText(ingredient: IngredientModel): String {
        return formatIngredientString(ingredient)
    }


}