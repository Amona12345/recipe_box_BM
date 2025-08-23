package com.example.recipebox.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "collection_recipe_cross_ref",
    primaryKeys = ["collectionId", "recipeId"],
    foreignKeys = [
        ForeignKey(
            entity = Collection::class,
            parentColumns = ["id"],
            childColumns = ["collectionId"]
        ),
        ForeignKey(entity = Recipe::class, parentColumns = ["id"], childColumns = ["recipeId"])
    ]
)
data class CollectionRecipeCrossRef(
    val collectionId: Int,
    val recipeId: Int
)

