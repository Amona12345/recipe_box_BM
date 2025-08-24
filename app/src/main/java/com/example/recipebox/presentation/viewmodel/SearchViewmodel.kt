package com.example.recipebox.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebox.data.dao.RecipeDao
import com.example.recipebox.data.entities.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchViewModel(private val recipeDao: RecipeDao) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<Recipe>>(emptyList())
    val searchResults: StateFlow<List<Recipe>> = _searchResults

    fun search(query: String?, selectedTags: String? = null) {
        viewModelScope.launch {
            recipeDao.searchRecipes(query, selectedTags).collectLatest { results ->
                _searchResults.value = results
            }
        }
    }
}
