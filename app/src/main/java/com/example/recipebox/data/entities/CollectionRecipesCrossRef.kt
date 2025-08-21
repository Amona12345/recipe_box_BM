package com.example.recipebox.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(primaryKeys = ["collectionId", "recipeId"])
data class CollectionRecipeCrossRef(
    val collectionId: Int,
    val recipeId: Int
)
data class CollectionWithRecipes(
    @Embedded val collection: Collection,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = CollectionRecipeCrossRef::class,
            parentColumn = "collectionId",
            entityColumn = "recipeId"
        )
    )
    val recipes: List<Recipe>
)


data class RecipeWithCollections(
    @Embedded val recipe: Recipe,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = CollectionWithRecipes::class,
            parentColumn = "recipeId",
            entityColumn = "collectionId"
        )
    )
    val collection: List<Collection>
)