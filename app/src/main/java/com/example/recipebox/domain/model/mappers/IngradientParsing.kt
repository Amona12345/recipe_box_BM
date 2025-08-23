package com.example.recipebox.domain.model.mappers

import com.example.recipebox.domain.model.IngredientModel

fun parseIngredientString(ingredientString: String): IngredientModel {
    val parts = ingredientString.trim().split("|") // Use | as separator for complex ingredients

    return if (parts.size >= 4) {
        IngredientModel(
            name = parts[0],
            amount = parts[1],
            unit = parts[2],
            notes = parts[3]
        )
    } else {
        val simpleParts = ingredientString.trim().split(" ")
        when {
            simpleParts.size >= 3 -> {
                val amount = simpleParts[0]
                val unit = simpleParts[1]
                val name = simpleParts.drop(2).joinToString(" ")
                IngredientModel(name = name, amount = amount, unit = unit)
            }
            simpleParts.size == 2 -> {
                val possibleAmount = simpleParts[0]
                val rest = simpleParts[1]
                if (possibleAmount.matches(Regex("\\d+(\\.\\d+)?|\\d+/\\d+"))) {
                    IngredientModel(name = rest, amount = possibleAmount, unit = "")
                } else {
                    IngredientModel(name = ingredientString, amount = "", unit = "")
                }
            }
            else -> IngredientModel(name = ingredientString, amount = "", unit = "")
        }
    }
}

fun formatIngredientString(ingredient: IngredientModel): String {
    return "${ingredient.name}|${ingredient.amount}|${ingredient.unit}|${ingredient.notes}"
}