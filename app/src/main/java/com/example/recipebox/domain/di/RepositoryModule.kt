package com.example.recipebox.domain.di

import com.example.recipebox.data.dao.CollectionDao
import com.example.recipebox.data.dao.RecipeDao
import com.example.recipebox.data.repository.CollectionRepositoryImpl
import com.example.recipebox.data.repository.RecipeRepositoryImpl
import com.example.recipebox.domain.repository.CollectionRepository
import com.example.recipebox.domain.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRecipeRepository(recipeDao: RecipeDao): RecipeRepository {
        return RecipeRepositoryImpl(recipeDao)
    }

    @Provides
    @Singleton
    fun provideCollectionRepository(collectionDao: CollectionDao): CollectionRepository {
        return CollectionRepositoryImpl(collectionDao)
    }
}