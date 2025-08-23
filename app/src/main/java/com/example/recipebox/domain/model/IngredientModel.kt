package com.example.recipebox.domain.model

data class IngredientModel(
    val name: String,
    val amount: String,
    val unit: String = "",
    val notes: String = ""
) {
    fun toDisplayString(): String {
        return buildString {
            if (amount.isNotBlank()) {
                append(amount)
                if (unit.isNotBlank()) {
                    append(" $unit")
                }
                append(" ")
            }
            append(name)
            if (notes.isNotBlank()) {
                append(" ($notes)")
            }
        }.trim()
    }
}
