package com.example.recipebox.domain.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.recipebox.data.dao.CollectionDao
import com.example.recipebox.data.dao.RecipeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import androidx.room.RoomDatabase
import com.example.recipebox.data.RecipeBoxDatabase

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RecipeBoxDatabase {
        return Room.databaseBuilder(
            context,
            RecipeBoxDatabase::class.java,
            "recipebox_database"
        ).build()
    }

    @Provides
    fun provideRecipeDao(database: RecipeBoxDatabase): RecipeDao = database.recipeDao()

    @Provides
    fun provideCollectionDao(database: RecipeBoxDatabase): CollectionDao = database.collectionDao()
}