package com.example.recipebox.data.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.recipebox.data.entities.Collection
import com.example.recipebox.data.entities.CollectionRecipeCrossRef
import com.example.recipebox.data.entities.Recipe

data class CollectionWithRecipes(
    @Embedded val collection: Collection,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            CollectionRecipeCrossRef::class,
            parentColumn = "collectionId",
            entityColumn = "recipeId"
        )
    )
    val recipes: List<Recipe>
)
