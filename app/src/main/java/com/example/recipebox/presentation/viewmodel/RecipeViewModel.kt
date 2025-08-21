package com.example.recipebox.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebox.domain.di.RecipeUseCases
import com.example.recipebox.domain.model.Ingredient
import com.example.recipebox.domain.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val useCases: RecipeUseCases
) : ViewModel() {

    val recipes: StateFlow<List<Recipe>> =
        useCases.getRecipes()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addRecipe(recipe: Recipe, ingredients: List<Ingredient>) {
        viewModelScope.launch {
            useCases.addRecipe(recipe, ingredients)
        }
    }
}
