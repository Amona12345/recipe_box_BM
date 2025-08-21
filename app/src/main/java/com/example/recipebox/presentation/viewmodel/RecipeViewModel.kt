package com.example.recipebox.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.recipebox.domain.repository.RecipeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepo
) : ViewModel() {   // <-- must extend ViewModel

    // Example state
    val recipes = repository.getRecipes()
}
