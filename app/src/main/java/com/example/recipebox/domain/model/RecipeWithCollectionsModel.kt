package com.example.recipebox.domain.model

data class RecipeWithCollectionsModel(
    val recipe: RecipeModel,
    val collections: List<CollectionModel>
)