package com.example.recipebox.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebox.core.ui_states.CollectionUiState
import com.example.recipebox.domain.model.CollectionModel
import com.example.recipebox.domain.model.CollectionWithRecipesModel
import com.example.recipebox.domain.usecase.collection.CollectionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val collectionUseCases: CollectionUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(CollectionUiState())
    val uiState: StateFlow<CollectionUiState> = _uiState.asStateFlow()

    val collectionsWithRecipes: StateFlow<List<CollectionWithRecipesModel>> =
        collectionUseCases.getCollectionsWithRecipes()
            .catch { exception ->
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to load collections: ${exception.message}"
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    val collections: StateFlow<List<CollectionModel>> =
        collectionUseCases.getCollections()
            .catch { exception ->
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to load collections: ${exception.message}"
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    suspend fun getCollectionWithRecipesFlow(collectionId: Int): StateFlow<CollectionWithRecipesModel?> {
        return collectionUseCases.getCollectionWithRecipesFlow(collectionId)
            .catch { exception ->
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to load collection details: ${exception.message}"
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )
    }

    fun createCollection(name: String) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
                val collectionId = collectionUseCases.createCollection(name)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    successMessage = "Collection '$name' created successfully"
                )
                delay(3000)
                _uiState.value = _uiState.value.copy(successMessage = null)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = when (e) {
                        is IllegalArgumentException -> e.message
                        else -> "Failed to create collection: ${e.message}"
                    }
                )
            }
        }
    }

    fun deleteCollection(collectionId: Int) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
                collectionUseCases.deleteCollection(collectionId)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    successMessage = "Collection deleted successfully"
                )
                delay(3000)
                _uiState.value = _uiState.value.copy(successMessage = null)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = when (e) {
                        is IllegalArgumentException -> e.message
                        else -> "Failed to delete collection: ${e.message}"
                    }
                )
            }
        }
    }

    fun updateCollection(collection: CollectionModel) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
                collectionUseCases.updateCollection(collection)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    successMessage = "Collection updated successfully"
                )
                delay(3000)
                _uiState.value = _uiState.value.copy(successMessage = null)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = when (e) {
                        is IllegalArgumentException -> e.message
                        else -> "Failed to update collection: ${e.message}"
                    }
                )
            }
        }
    }

    fun addRecipeToCollection(collectionId: Int, recipeId: Int) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
                collectionUseCases.addRecipeToCollection(collectionId, recipeId)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    successMessage = "Recipe added to collection successfully"
                )
                delay(3000)
                _uiState.value = _uiState.value.copy(successMessage = null)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = when (e) {
                        is IllegalArgumentException -> e.message ?: "Recipe already in collection"
                        else -> "Failed to add recipe to collection: ${e.message}"
                    }
                )
            }
        }
    }

    fun removeRecipeFromCollection(collectionId: Int, recipeId: Int) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
                collectionUseCases.removeRecipeFromCollection(collectionId, recipeId)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    successMessage = "Recipe removed from collection successfully"
                )
                delay(3000)
                _uiState.value = _uiState.value.copy(successMessage = null)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = when (e) {
                        is IllegalArgumentException -> e.message
                        else -> "Failed to remove recipe from collection: ${e.message}"
                    }
                )
            }
        }
    }

    fun addRecipesToCollection(collectionId: Int, recipeIds: List<Int>) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

                var successCount = 0
                var errorCount = 0

                recipeIds.forEach { recipeId ->
                    try {
                        collectionUseCases.addRecipeToCollection(collectionId, recipeId)
                        successCount++
                    } catch (e: Exception) {
                        errorCount++
                    }
                }

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    successMessage = if (errorCount == 0) {
                        "${successCount} recipe${if (successCount > 1) "s" else ""} added successfully"
                    } else {
                        "${successCount} added, ${errorCount} failed (might already be in collection)"
                    }
                )

                delay(3000)
                _uiState.value = _uiState.value.copy(successMessage = null)

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to add recipes to collection: ${e.message}"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun clearSuccess() {
        _uiState.value = _uiState.value.copy(successMessage = null)
    }

    fun clearLoading() {
        _uiState.value = _uiState.value.copy(isLoading = false)
    }

    override fun onCleared() {
        super.onCleared()
    }
}