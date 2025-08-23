package com.example.recipebox.data.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.recipebox.data.entities.CollectionRecipeCrossRef
import com.example.recipebox.data.entities.Recipe
import com.example.recipebox.data.entities.Collection


data class RecipeWithCollections(
    @Embedded val recipe: Recipe,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = CollectionRecipeCrossRef::class,
            parentColumn = "recipeId",
            entityColumn = "collectionId"
        )
    )
    val collections: List<Collection>
)