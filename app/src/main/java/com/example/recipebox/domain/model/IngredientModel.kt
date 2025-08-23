package com.example.recipebox.domain.model

data class IngredientModel(
    val name: String,
    val amount: String,
    val unit: String = ""
)