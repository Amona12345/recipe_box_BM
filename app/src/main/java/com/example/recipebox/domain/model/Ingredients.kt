package com.example.recipebox.domain.model

data class Ingredient(
    val id: Int = 0,
    val recipeId: Int = 0,
    val name: String,
    val quantity: String
)