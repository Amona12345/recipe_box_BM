package com.example.recipebox.domain.model.mappers
import com.example.recipebox.data.entities.Collection
import com.example.recipebox.domain.model.CollectionModel

fun Collection.toDomain(): CollectionModel {
    return CollectionModel(
        id = id,
        name = name,
        createdAt = createdAt
    )
}

fun CollectionModel.toData(): Collection {
    return Collection(
        id = id,
        name = name,
        createdAt = createdAt
    )
}