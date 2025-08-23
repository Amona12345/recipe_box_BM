package com.example.recipebox.domain.model.mappers

import com.example.recipebox.core.enums.DifficultyEnum
import com.example.recipebox.data.entities.Recipe
import com.example.recipebox.domain.model.RecipeModel

fun Recipe.toDomain(): RecipeModel {
    return RecipeModel(
        id = id,
        title = title,
        description = description,
        imageUri = imageUri,
        localImagePath = localImagePath,
        tags = tags,
        ingredients = ingredients.map { parseIngredientString(it) },
        steps = steps,
        servings = servings,
        prepTime = prepTime,
        cookTime = cookTime,
        totalTime = totalTime,
        difficulty = difficulty, // Direct assignment - all properties preserved
        dishType = dishType,
        dietaryTags = dietaryTags,
        cuisine = cuisine,
        calories = calories,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun RecipeModel.toData(): Recipe {
    return Recipe(
        id = id,
        title = title,
        description = description,
        imageUri = imageUri,
        localImagePath = localImagePath,
        tags = tags,
        ingredients = ingredients.map { formatIngredientString(it) },
        steps = steps,
        servings = servings,
        prepTime = prepTime,
        cookTime = cookTime,
        totalTime = totalTime,
        difficulty = difficulty, // Direct assignment - all properties preserved
        dishType = dishType,
        dietaryTags = dietaryTags,
        cuisine = cuisine,
        calories = calories,
        createdAt = createdAt,
        updatedAt = System.currentTimeMillis()
    )
}