package com.example.recipebox.domain.model

data class CollectionModel(
    val id: Int = 0,
    val name: String,
    val createdAt: Long = System.currentTimeMillis()
)