package com.example.recipebox.domain.di

import com.example.recipebox.data.dao.RecipeDao
import com.example.recipebox.data.repository.RecipeRepositoryImpl
import com.example.recipebox.domain.repository.RecipeRepo
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
    fun provideRecipeRepository(
        recipeDao: RecipeDao
    ): RecipeRepo {
        return RecipeRepositoryImpl(recipeDao)
    }
}