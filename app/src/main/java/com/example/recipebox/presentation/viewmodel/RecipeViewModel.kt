package com.example.recipebox.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebox.core.ui_states.RecipeUiState
import com.example.recipebox.domain.model.CollectionModel
import com.example.recipebox.domain.model.RecipeModel
import com.example.recipebox.domain.usecase.collection.CollectionUseCases
import com.example.recipebox.domain.usecase.recipe.RecipeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeUseCases: RecipeUseCases,
    private val collectionUseCases: CollectionUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeUiState())
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedTags = MutableStateFlow<List<String>>(emptyList())
    val selectedTags: StateFlow<List<String>> = _selectedTags.asStateFlow()

    val allRecipes: StateFlow<List<RecipeModel>> = recipeUseCases.getRecipes()
        .onStart {
            _uiState.value = _uiState.value.copy(isLoading = true)
        }
        .catch { exception ->
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                errorMessage = "Failed to load recipes: ${exception.message}"
            )
            emit(emptyList())
        }
        .onEach {
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val recipes: StateFlow<List<RecipeModel>> = combine(
        allRecipes,
        _searchQuery,
        _selectedTags
    ) { allRecipes, query, tags ->
        var filteredRecipes = allRecipes

        if (query.isNotBlank()) {
            filteredRecipes = filteredRecipes.filter { recipe ->
                val titleMatch = recipe.title.contains(query, ignoreCase = true)
                val descriptionMatch = recipe.description.contains(query, ignoreCase = true)

                val ingredientMatch = try {
                    when (val ingredients = recipe.ingredients) {
                        else -> ingredients.any { it.toString().contains(query, ignoreCase = true) }
                    }
                } catch (e: Exception) {
                    false
                }

                val stepsMatch = try {
                    when (val steps = recipe.steps) {
                        else -> steps.any { it.toString().contains(query, ignoreCase = true) }
                    }
                } catch (e: Exception) {
                    false
                }

                titleMatch || descriptionMatch || ingredientMatch || stepsMatch
            }
        }
        if (tags.isNotEmpty()) {
            filteredRecipes = filteredRecipes.filter { recipe ->
                val allRecipeTags = recipe.tags + recipe.dietaryTags
                tags.any { selectedTag ->
                    allRecipeTags.any { recipeTag ->
                        recipeTag.equals(selectedTag, ignoreCase = true)
                    }
                }
            }
        }

        filteredRecipes
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            allRecipes.collect { recipes ->
                if (_uiState.value.isLoading) {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onTagToggle(tag: String) {
        val currentTags = _selectedTags.value.toMutableList()
        if (currentTags.contains(tag)) {
            currentTags.remove(tag)
        } else {
            currentTags.add(tag)
        }
        _selectedTags.value = currentTags
    }

    fun clearFilters() {
        _searchQuery.value = ""
        _selectedTags.value = emptyList()
    }

    fun addRecipe(recipe: RecipeModel) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                recipeUseCases.addRecipe(recipe)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    successMessage = "Recipe added successfully"
                )
                // Auto-clear success message
                kotlinx.coroutines.delay(3000)
                _uiState.value = _uiState.value.copy(successMessage = null)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Failed to add recipe"
                )
            }
        }
    }

    fun updateRecipe(recipe: RecipeModel) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                recipeUseCases.updateRecipe(recipe)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    successMessage = "Recipe updated successfully"
                )
                // Auto-clear success message
                kotlinx.coroutines.delay(3000)
                _uiState.value = _uiState.value.copy(successMessage = null)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Failed to update recipe"
                )
            }
        }
    }

    fun deleteRecipe(recipe: RecipeModel) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                recipeUseCases.deleteRecipe(recipe)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    successMessage = "Recipe deleted successfully"
                )
                // Auto-clear success message
                kotlinx.coroutines.delay(3000)
                _uiState.value = _uiState.value.copy(successMessage = null)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Failed to delete recipe"
                )
            }
        }
    }

    suspend fun getRecipeById(id: Int): RecipeModel? {
        return try {
            recipeUseCases.getRecipeById(id)
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Failed to load recipe: ${e.message}"
            )
            null
        }
    }

    fun getAllAvailableTags(): List<String> {
        return allRecipes.value.flatMap { it.tags + it.dietaryTags }.distinct().sorted()
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun clearSuccess() {
        _uiState.value = _uiState.value.copy(successMessage = null)
    }
}