package com.example.recipebox.domain.model.mappers

import com.example.recipebox.domain.model.IngredientModel

fun parseIngredientString(ingredientString: String): IngredientModel {
    val parts = ingredientString.trim().split(" ")
    return when {
        parts.size >= 3 -> {
            val amount = parts[0]
            val unit = parts[1]
            val name = parts.drop(2).joinToString(" ")
            IngredientModel(name = name, amount = amount, unit = unit)
        }
        parts.size == 2 -> {
            // Try to determine if second part is unit or part of name
            val possibleAmount = parts[0]
            val rest = parts[1]
            if (possibleAmount.matches(Regex("\\d+(\\.\\d+)?")) ||
                possibleAmount.matches(Regex("\\d+/\\d+"))) {
                IngredientModel(name = rest, amount = possibleAmount, unit = "")
            } else {
                IngredientModel(name = ingredientString, amount = "", unit = "")
            }
        }
        else -> IngredientModel(name = ingredientString, amount = "", unit = "")
    }
}

fun formatIngredientString(ingredient: IngredientModel): String {
    return buildString {
        if (ingredient.amount.isNotBlank()) {
            append(ingredient.amount)
            append(" ")
        }
        if (ingredient.unit.isNotBlank()) {
            append(ingredient.unit)
            append(" ")
        }
        append(ingredient.name)
    }.trim()
}