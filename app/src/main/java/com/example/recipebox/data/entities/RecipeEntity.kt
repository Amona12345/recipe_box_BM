package com.example.recipebox.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val imageUri: String?,
    val tags: List<String>,
    val ingredients: List<String>,
    val steps: List<String>
)