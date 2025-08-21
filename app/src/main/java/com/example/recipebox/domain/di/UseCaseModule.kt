package com.example.recipebox.domain.di

import com.example.recipebox.domain.repository.RecipeRepo
import com.example.recipebox.domain.usecase.AddRecipeUseCase
import com.example.recipebox.domain.usecase.GetRecipeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

data class RecipeUseCases(
    val addRecipe: AddRecipeUseCase,
    val getRecipes: GetRecipeUseCase

    )

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideRecipeUseCases(repo: RecipeRepo): RecipeUseCases {
        return RecipeUseCases(
            addRecipe = AddRecipeUseCase(repo),
            getRecipes = GetRecipeUseCase(repo)
        )
    }}