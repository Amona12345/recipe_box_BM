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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
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

    @OptIn(ExperimentalCoroutinesApi::class)
    val recipes: StateFlow<List<RecipeModel>> = combine(
        _searchQuery,
        _selectedTags
    ) { query, tags ->
        recipeUseCases.searchRecipes(
            query = query.takeIf { it.isNotBlank() },
            tags = tags.takeIf { it.isNotEmpty() }
        )
    }.flatMapLatest { it }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val collections: StateFlow<List<CollectionModel>> = collectionUseCases.getCollections()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

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
                _uiState.value = _uiState.value.copy(isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }

    fun updateRecipe(recipe: RecipeModel) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                recipeUseCases.updateRecipe(recipe)
                _uiState.value = _uiState.value.copy(isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }

    fun deleteRecipe(recipe: RecipeModel) {
        viewModelScope.launch {
            try {
                recipeUseCases.deleteRecipe(recipe)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun getRecipeById(id: Int, callback: (RecipeModel?) -> Unit) {
        viewModelScope.launch {
            try {
                val recipe = recipeUseCases.getRecipeById(id)
                callback(recipe)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
                callback(null)
            }
        }
    }

    fun addRecipeToCollection(recipeId: Int, collectionId: Int) {
        viewModelScope.launch {
            try {
                collectionUseCases.addRecipeToCollection(collectionId, recipeId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun getAllAvailableTags(): List<String> {
        return recipes.value.flatMap { it.tags }.distinct().sorted()
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
