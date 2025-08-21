package com.example.recipebox.data.converters

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromList(list: List<String>?): String = list?.joinToString("||") ?: ""

    @TypeConverter
    fun toList(csv: String?): List<String> = csv?.takeIf { it.isNotBlank() }?.split("||") ?: emptyList()
}