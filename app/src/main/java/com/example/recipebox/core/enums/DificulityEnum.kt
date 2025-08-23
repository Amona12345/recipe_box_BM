package com.example.recipebox.core.enums


enum class DifficultyEnum(val displayName: String, val color: Long) {
    EASY("Easy", 0xFF4CAF50),
    MEDIUM("Medium", 0xFFFF9800),
    HARD("Hard", 0xFFF44336);

    // Convert to storage format
    fun toStorageString(): String {
        return "$name|$displayName|$color"
    }

    companion object {
        fun fromStorageString(data: String): DifficultyEnum {
            return try {
                val parts = data.split("|")
                if (parts.isNotEmpty()) {
                    entries.find { it.name == parts[0] } ?: EASY
                } else {
                    entries.find { it.name == data } ?: EASY
                }
            } catch (e: Exception) {
                EASY
            }
        }
    }
}