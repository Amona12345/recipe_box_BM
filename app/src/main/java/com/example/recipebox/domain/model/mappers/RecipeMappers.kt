package com.example.recipebox.domain.model.mappers

import com.example.recipebox.core.enums.DifficultyEnum
import com.example.recipebox.data.entities.Recipe
import com.example.recipebox.domain.model.RecipeModel

fun Recipe.toDomain(): RecipeModel {
    return RecipeModel(
        id = id,
        title = title,
        imageUri = imageUri,
        tags = tags,
        ingredients = ingredients.map { ingredientString ->
            parseIngredientString(ingredientString)
        },
        steps = steps,
        servings = servings,
        cookingTime = cookingTime,
        difficulty = when (difficulty.lowercase()) {
            "easy" -> DifficultyEnum.EASY
            "medium" -> DifficultyEnum.MEDIUM
            "hard" -> DifficultyEnum.HARD
            else -> DifficultyEnum.EASY
        }
    )
}

fun RecipeModel.toData(): Recipe {
    return Recipe(
        id = id,
        title = title,
        imageUri = imageUri,
        tags = tags,
        ingredients = ingredients.map { ingredient ->
            formatIngredientString(ingredient)
        },
        steps = steps,
        servings = servings,
        cookingTime = cookingTime,
        difficulty = difficulty.name.lowercase()
    )
}