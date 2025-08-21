package com.example.recipebox.domain.model


data class Recipe(
    val id: Int = 0,
    val title: String,
    val imagePath: String? = null,
    val tags: List<String> = emptyList()
)