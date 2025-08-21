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

@Database(entities = [Recipe::class, Collection::class, CollectionRecipeCrossRef::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class Database: RoomDatabase(){
    abstract fun recipesDao(): RecipeDao
    abstract fun collectionDao(): CollectionDao

}