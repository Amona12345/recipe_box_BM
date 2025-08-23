package com.example.recipebox.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipebox.data.converters.Converters
import com.example.recipebox.data.dao.CollectionDao
import com.example.recipebox.data.dao.RecipeDao
import com.example.recipebox.data.entities.Collection
import com.example.recipebox.data.entities.CollectionRecipeCrossRef
import com.example.recipebox.data.entities.Recipe

@TypeConverters(Converters::class)
@Database(
    entities = [Recipe::class, Collection::class, CollectionRecipeCrossRef::class],
    version = 1,
    exportSchema = false
)
abstract class RecipeBoxDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun collectionDao(): CollectionDao
}