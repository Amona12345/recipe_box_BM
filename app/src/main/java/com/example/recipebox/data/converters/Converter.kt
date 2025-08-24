package com.example.recipebox.data.converters

import androidx.room.TypeConverter
import com.example.recipebox.core.enums.DifficultyEnum

class Converters {
    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return list.joinToString("||")
    }

    @TypeConverter
    fun toStringList(data: String): List<String> {
        return if (data.isBlank()) emptyList() else data.split("||")
    }

    @TypeConverter
    fun fromDifficultyEnum(difficulty: DifficultyEnum): String {
        return difficulty.toStorageString()
    }

    @TypeConverter
    fun toDifficultyEnum(data: String): DifficultyEnum {
        return DifficultyEnum.fromStorageString(data)
    }
}