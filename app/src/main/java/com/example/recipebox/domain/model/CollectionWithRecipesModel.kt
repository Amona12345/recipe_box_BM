package com.example.recipebox.domain.model

data class CollectionWithRecipesModel(
    val collection: CollectionModel,
    val recipes: List<RecipeModel>
)