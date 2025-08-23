package com.example.recipebox.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebox.core.ui_states.CollectionUiState
import com.example.recipebox.domain.model.CollectionModel
import com.example.recipebox.domain.model.CollectionWithRecipesModel
import com.example.recipebox.domain.usecase.collection.CollectionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val collections: StateFlow<List<CollectionModel>> =
        collectionUseCases.getCollections()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun createCollection(name: String) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                collectionUseCases.createCollection(name)
                _uiState.value = _uiState.value.copy(isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }

    fun deleteCollection(collectionId: Int) {
        viewModelScope.launch {
            try {
                collectionUseCases.deleteCollection(collectionId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun updateCollection(collection: CollectionModel) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                collectionUseCases.updateCollection(collection)
                _uiState.value = _uiState.value.copy(isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }

    fun addRecipeToCollection(collectionId: Int, recipeId: Int) {
        viewModelScope.launch {
            try {
                collectionUseCases.addRecipeToCollection(collectionId, recipeId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun removeRecipeFromCollection(collectionId: Int, recipeId: Int) {
        viewModelScope.launch {
            try {
                collectionUseCases.removeRecipeFromCollection(collectionId, recipeId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}