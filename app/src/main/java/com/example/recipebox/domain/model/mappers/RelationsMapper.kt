package com.example.recipebox.domain.model.mappers

import com.example.recipebox.data.relations.CollectionWithRecipes
import com.example.recipebox.data.relations.RecipeWithCollections
import com.example.recipebox.domain.model.CollectionWithRecipesModel
import com.example.recipebox.domain.model.RecipeWithCollectionsModel

fun CollectionWithRecipes.toDomain(): CollectionWithRecipesModel {
    return CollectionWithRecipesModel(
        collection = collection.toDomain(),
        recipes = recipes.map { it.toDomain() }
    )
}

fun RecipeWithCollections.toDomain(): RecipeWithCollectionsModel {
    return RecipeWithCollectionsModel(
        recipe = recipe.toDomain(),
        collections = collections.map { it.toDomain() }
    )
}