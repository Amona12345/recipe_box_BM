package com.example.recipebox.presentation.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebox.core.enums.DifficultyEnum
import com.example.recipebox.core.ui_states.AddRecipeUiState
import com.example.recipebox.domain.model.IngredientModel
import com.example.recipebox.domain.model.RecipeModel
import com.example.recipebox.domain.model.mappers.formatIngredientString
import com.example.recipebox.domain.model.mappers.parseIngredientString
import com.example.recipebox.domain.repository.ImageRepository
import com.example.recipebox.domain.usecase.collection.CollectionUseCases
import com.example.recipebox.domain.usecase.recipe.ImageUseCases
import com.example.recipebox.domain.usecase.recipe.RecipeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddRecipeViewModel @Inject constructor(
    private val recipeUseCases: RecipeUseCases,
    private val imageUseCases: ImageUseCases,
    private val collectionUseCases: CollectionUseCases // Add this dependency
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddRecipeUiState())
    val uiState: StateFlow<AddRecipeUiState> = _uiState.asStateFlow()

    fun nextStep() {
        val currentStep = _uiState.value.currentStep
        if (currentStep < 3) {
            _uiState.value = _uiState.value.copy(currentStep = currentStep + 1)
        }
    }

    fun previousStep() {
        val currentStep = _uiState.value.currentStep
        if (currentStep > 0) {
            _uiState.value = _uiState.value.copy(currentStep = currentStep - 1)
        }
    }

    fun goToStep(step: Int) {
        if (step in 0..3) {
            _uiState.value = _uiState.value.copy(currentStep = step)
        }
    }

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun updateDescription(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun updateServings(servings: Int) {
        _uiState.value = _uiState.value.copy(servings = servings)
    }

    fun updatePrepTime(time: String) {
        _uiState.value = _uiState.value.copy(prepTime = time)
    }

    fun updateCookTime(time: String) {
        _uiState.value = _uiState.value.copy(cookTime = time)
    }

    fun updateDifficulty(difficulty: DifficultyEnum) {
        _uiState.value = _uiState.value.copy(difficulty = difficulty)
    }

    fun updateDishType(dishType: String) {
        _uiState.value = _uiState.value.copy(dishType = dishType)
    }

    fun updateCuisine(cuisine: String) {
        _uiState.value = _uiState.value.copy(cuisine = cuisine)
    }

    fun updateCalories(calories: String) {
        _uiState.value = _uiState.value.copy(calories = calories)
    }

    fun showImagePickerDialog() {
        _uiState.value = _uiState.value.copy(showImagePickerDialog = true)
    }

    fun hideImagePickerDialog() {
        _uiState.value = _uiState.value.copy(showImagePickerDialog = false)
    }





    fun removeImage() {
        viewModelScope.launch {
            _uiState.value.localImagePath?.let { path ->
                imageUseCases.deleteImage(path)
            }
            _uiState.value = _uiState.value.copy(
                imageUri = null,
                localImagePath = null
            )
        }
    }

    fun toggleDietaryTag(tag: String) {
        val currentTags = _uiState.value.dietaryTags.toMutableList()
        if (currentTags.contains(tag)) {
            currentTags.remove(tag)
        } else {
            currentTags.add(tag)
        }
        _uiState.value = _uiState.value.copy(dietaryTags = currentTags)
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

    fun addIngredient() {
        val currentIngredients = _uiState.value.ingredients.toMutableList()
        currentIngredients.add(IngredientModel("", "", "", ""))
        _uiState.value = _uiState.value.copy(ingredients = currentIngredients)
    }

    fun removeIngredient(index: Int) {
        val currentIngredients = _uiState.value.ingredients.toMutableList()
        if (index in currentIngredients.indices && currentIngredients.size > 1) {
            currentIngredients.removeAt(index)
            _uiState.value = _uiState.value.copy(ingredients = currentIngredients)
        }
    }

    fun updateIngredient(index: Int, name: String, amount: String, unit: String, notes: String = "") {
        val currentIngredients = _uiState.value.ingredients.toMutableList()
        if (index in currentIngredients.indices) {
            currentIngredients[index] = IngredientModel(name, amount, unit, notes)
            _uiState.value = _uiState.value.copy(ingredients = currentIngredients)
        }
    }

    fun addStep() {
        val currentSteps = _uiState.value.steps.toMutableList()
        currentSteps.add("")
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

    fun isStepValid(step: Int): Boolean {
        return when (step) {
            0 -> _uiState.value.title.isNotBlank()
            1 -> _uiState.value.ingredients.any { it.name.isNotBlank() }
            2 -> _uiState.value.steps.any { it.isNotBlank() }
            else -> true
        }
    }

    fun canProceedToNext(): Boolean {
        return isStepValid(_uiState.value.currentStep)
    }
    fun setTargetCollectionId(collectionId: Int) {
        _uiState.value = _uiState.value.copy(targetCollectionId = collectionId)
    }
    fun saveRecipe() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)

                val currentState = _uiState.value
                val recipe = RecipeModel(
                    title = currentState.title.trim(),
                    description = currentState.description.trim(),
                    imageUri = currentState.imageUri,
                    localImagePath = currentState.localImagePath,
                    ingredients = currentState.ingredients.filter { it.name.isNotBlank() },
                    steps = currentState.steps.filter { it.isNotBlank() },
                    tags = currentState.tags,
                    servings = currentState.servings,
                    prepTime = currentState.prepTime.trim(),
                    cookTime = currentState.cookTime.trim(),
                    totalTime = calculateTotalTime(currentState.prepTime, currentState.cookTime),
                    difficulty = currentState.difficulty,
                    dishType = currentState.dishType,
                    dietaryTags = currentState.dietaryTags,
                    cuisine = currentState.cuisine,
                    calories = currentState.calories.toIntOrNull()
                )

                val recipeId = recipeUseCases.addRecipe(recipe)

                currentState.targetCollectionId?.let { collectionId ->
                    try {
                        collectionUseCases.addRecipeToCollection(collectionId, recipeId.toInt())
                    } catch (e: Exception) {
                        Log.e("AddRecipeViewModel", "Failed to add recipe to collection: ${e.message}")
                    }
                }

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

    private fun calculateTotalTime(prepTime: String, cookTime: String): String {
        return when {
            prepTime.isBlank() && cookTime.isBlank() -> ""
            prepTime.isBlank() -> cookTime
            cookTime.isBlank() -> prepTime
            else -> {
                val prepMinutes = extractMinutes(prepTime)
                val cookMinutes = extractMinutes(cookTime)
                val totalMinutes = prepMinutes + cookMinutes

                when {
                    totalMinutes >= 60 -> "${totalMinutes / 60}h ${totalMinutes % 60}min"
                    else -> "${totalMinutes}min"
                }
            }
        }
    }

    private fun extractMinutes(timeString: String): Int {
        return try {
            val numbers = timeString.filter { it.isDigit() || it == '.' }
            if (numbers.isNotBlank()) {
                val value = numbers.toDouble()
                when {
                    timeString.contains("hour", ignoreCase = true) || timeString.contains("h", ignoreCase = true) -> (value * 60).toInt()
                    else -> value.toInt()
                }
            } else 0
        } catch (e: Exception) {
            0
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun resetForm() {
        _uiState.value.localImagePath?.let { path ->
            viewModelScope.launch {
                imageUseCases.deleteImage(path)
            }
        }
        _uiState.value = AddRecipeUiState()
    }


    fun showError(message: String) {
        _uiState.value = _uiState.value.copy(errorMessage = message)
    }


    fun updateImageFromCamera(uri: Uri) {
        viewModelScope.launch {
            try {
                // Check if file exists
                val file = File(uri.path ?: "")
                if (!file.exists()) {
                    _uiState.value = _uiState.value.copy(
                        errorMessage = "Image file was not created by camera"
                    )
                    return@launch
                }

                val savedPath = imageUseCases.saveImageToInternalStorage(uri)
                _uiState.value = _uiState.value.copy(
                    imageUri = uri.toString(),
                    localImagePath = savedPath,
                    showImagePickerDialog = false
                )
            } catch (e: Exception) {
                Log.e("CameraError", "Failed to save image from camera", e)
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to save image: ${e.message}",
                    showImagePickerDialog = false
                )
            }
        }
    }

    fun updateImageFromGallery(uri: Uri) {
        viewModelScope.launch {
            try {
                val savedPath = imageUseCases.saveImageToInternalStorage(uri)
                _uiState.value = _uiState.value.copy(
                    imageUri = uri.toString(),
                    localImagePath = savedPath,
                    showImagePickerDialog = false
                )
            } catch (e: Exception) {
                Log.e("GalleryError", "Failed to save image from gallery", e)
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to save image: ${e.message}",
                    showImagePickerDialog = false
                )
            }
        }
    }



    override fun onCleared() {
        super.onCleared()
        if (!_uiState.value.isSaved) {
            _uiState.value.localImagePath?.let { path ->
                viewModelScope.launch {
                    imageUseCases.deleteImage(path)
                }
            }
        }
    }
    fun clearTargetCollection() {
        _uiState.value = _uiState.value.copy(targetCollectionId = null)
    }
}